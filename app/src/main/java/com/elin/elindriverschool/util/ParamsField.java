/**
 * 
 */
package com.elin.elindriverschool.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kinglone
 * 2015-8-16下午12:17:36
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ParamsField {
	//指定网络参数的字段名
	public String pName();
}
