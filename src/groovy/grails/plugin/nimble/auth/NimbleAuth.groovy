package grails.plugin.nimble.auth

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NimbleAuth {

    /**
     * A collection of permissions to validate
     * @return
     */
    String permission() default "";

    /**
     * A collection of groups
     * @return
     */
    String role() default "";
}