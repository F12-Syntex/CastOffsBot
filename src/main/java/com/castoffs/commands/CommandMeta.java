package com.castoffs.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jetbrains.annotations.NotNull;

/**
 * This annotation is used to provide meta information about a command
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandMeta {
    @NotNull String name();
    @NotNull String description();
    @NotNull Category category();
    @NotNull long cooldown() default 0L;
}
