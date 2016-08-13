package com.elin4it.util.convert;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD ,ElementType.TYPE})
public @interface ServiceUseAction {
	
	String value() default "";
	int index() default -1;
	String alias() default "";
}
