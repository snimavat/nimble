package grails.plugin.nimble.auth

import org.apache.commons.lang.StringUtils
import org.apache.log4j.Logger
import org.apache.shiro.subject.Subject
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.expression.EvaluationContext
import org.springframework.expression.Expression
import org.springframework.expression.ParserContext
import org.springframework.expression.common.TemplateParserContext
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext

import java.lang.reflect.Method

@Aspect
public class NimbleAuthAspect {

    private static final Logger log = Logger.getLogger(NimbleAuthAspect.class)
    public static final SpelExpressionParser spelExpressionParser = new SpelExpressionParser()
    public static final ParserContext parserContext = new TemplateParserContext()

    @Pointcut("@annotation(grails.plugin.nimble.auth.NimbleAuth)")
    public void doNimbleAuth() {}

    @Around("grails.plugin.nimble.auth.NimbleAuthAspect.doNimbleAuth()")
    public Object executeAuth(ProceedingJoinPoint pjp) throws Throwable {

        def target = pjp.getTarget()

        MethodSignature methodSignature = pjp.getSignature();
        Method method = methodSignature.method;
        NimbleAuth authPref = method.getAnnotation(NimbleAuth.class)

        def role = authPref.role()
        def permission = authPref.permission()

        if (log.isDebugEnabled()) {
            log.debug("Invoked @NimbleAuth processing for = " + target.getClass())
        }

        if (target.metaClass.respondsTo(target, "getAuthenticatedSubject")) {

            Subject subject = target.getAuthenticatedSubject()
            def request = target.request

            // Check authentication flag
            def subjectAuthenticated = subject.authenticated
            log.debug("Is subject authenticated = ${subjectAuthenticated}")

            // Re-direct to login page
            if (!subjectAuthenticated) {
                redirectToLoginPage(request, target)
            } else {

                log.debug("Subject is authenticated")

                // Check role if specified
                if (StringUtils.isNotEmpty(role)) {
                    checkSubjectRole(subject, role, target)
                }

                // Check permission if specified
                if (StringUtils.isNotEmpty(permission)) {
                    evaluatePermission(permission, target, subject)
                }
            }
        }

        return pjp.proceed();
    }

    private void evaluatePermission(String permission, target, Subject subject) {
        Expression expression = spelExpressionParser.parseExpression(permission, parserContext);
        EvaluationContext ectx = new StandardEvaluationContext(target)
        ectx.setVariable("params", target.params)
        Object value = expression.getValue(ectx)

        if (log.isDebugEnabled()) {
            log.debug("Checking subject ${subject.principal} for permission ${value} ");
        }

        boolean hasPermission = subject.isPermitted(value.toString())

        if (!hasPermission) {
            log.info("Subject ${subject.principal} does not have permission ${value} ");
            target.redirect(
                    controller: "auth",
                    action: "unauthorized")
        }
    }

    private void checkSubjectRole(Subject subject, role, target) {
        if (log.isDebugEnabled()) {
            log.debug("Checking subject ${subject.principal} for role ${role} ");
        }

        boolean hasRequestedRole = subject.hasRole(role)

        if (!hasRequestedRole) {
            log.info("Subject ${subject.principal} does not have role ${role} ");
            target.redirect(
                    controller: "auth",
                    action: "unauthorized")
        }
    }

    /**
     * Re-direct to login page if required
     * @param request Incoming HTTP request
     * @param target Controller, service or filter executing this operation
     */
    private void redirectToLoginPage(request, target) {

        log.info("Subject not authenticated. Re-directing to login page...")

        def targetUri = new StringBuilder(request.forwardURI[request.contextPath.size()..-1])
        def query = request.queryString
        if (query) {
            if (!query.startsWith('?')) {
                targetUri << '?'
            }
            targetUri << query
        }

        def redirectUri = target.grailsApplication.config.security.shiro.redirect.uri
        if (redirectUri) {
            target.redirect(uri: redirectUri + "?targetUri=${targetUri.encodeAsURL()}")
        } else {
            target.redirect(
                    controller: "auth",
                    action: "login",
                    params: [targetUri: targetUri.toString()])
        }
    }
}

