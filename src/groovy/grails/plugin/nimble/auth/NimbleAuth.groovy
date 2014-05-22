package grails.plugin.nimble.auth

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * An annotation to enforce authentication for a controller method. In addition to
 * enforcing authentication, you can also specify the role and permission criteria
 * the user should satisfy in order to proceed.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NimbleAuth {

    /**
     * The current subject should have this permission to
     * proceed with the execution flow. The user will be re-directed to the
     * /unauthorized page if this permission does not exist.
     *
     * This permission can be specified using Spring Expression Language
     * e.g. @NimbleAuth(role = 'USER', permission = 'entity:action:#{params[\'id\']}')
     * @return
     */
    String permission() default "";

    /**
     * The currently logged in user should belong to this role to proceed
     * with the execution. The user will be re-directed to the
     * /unauthorized page if this permission does not exist.
     * @return
     */
    String role() default "";
}