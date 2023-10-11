package com.castoffs.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

/**
 * This annotation is used to provide meta information about a command
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandMeta {
    @NotNull @Nonnull String[] alias();
    @NotNull @Nonnull String description();
    @NotNull @Nonnull Category category();
    @NotNull @Nonnull String[] usage();
    @NotNull @Nonnull String[] examples();
    @NotNull boolean nsfw() default false;
    @NotNull boolean completed() default true;
    @NotNull long cooldown() default 0L;
}
