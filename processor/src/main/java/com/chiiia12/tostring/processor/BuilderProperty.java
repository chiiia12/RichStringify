package com.chiiia12.tostring.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)//means it is only available during source processing and is not available at runtime
public @interface BuilderProperty {
}
