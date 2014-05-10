package grails.plugin.nimble.auth

import org.apache.commons.lang.StringUtils
import org.apache.log4j.Logger
import org.apache.shiro.subject.Subject
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

import java.lang.reflect.Method

@Aspect
public class NimbleAuthAspect {

    private static final Logger log = Logger.getLogger(NimbleAuthAspect.class)

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
            def subjectAuthenticated = subject.authenticated

            if (log.isDebugEnabled()) {
                log.debug("Is subject authenticated = ${subjectAuthenticated}")
            }

            def request = target.request
            if (!subjectAuthenticated) {

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
            } else {

                if (log.isDebugEnabled()) {
                    log.debug("Subject is authenticated")
                }

                // Check role if specified
                if (StringUtils.isNotEmpty(role)) {

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

                if (StringUtils.isNotEmpty(permission)) {

                    if (log.isDebugEnabled()) {
                        log.debug("Checking subject ${subject.principal} for permission ${permission} ");
                    }

                    boolean hasPermission = subject.isPermitted(permission)

                    if (!hasPermission) {
                        log.info("Subject ${subject.principal} does not have permission ${permission} ");
                        target.redirect(
                                controller: "auth",
                                action: "unauthorized")
                    }
                }
            }
        }

        return pjp.proceed();
    }
}

