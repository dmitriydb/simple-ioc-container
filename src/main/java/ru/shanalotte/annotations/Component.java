package ru.shanalotte.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация, которой помечаются объекты, в которые необходимо внедрить объекты класса Box
 *
 * @version 1.1
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
    BoxType type() default BoxType.SINGLETON;
    String name() default "";
}
