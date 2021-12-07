package ru.shanalotte.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация, которая определяет класс, объекты которого могут быть внедрены в другие объекты
 *
 * @version 1.0
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Box {
    BoxType type() default BoxType.SINGLETON;
    String name();
}
