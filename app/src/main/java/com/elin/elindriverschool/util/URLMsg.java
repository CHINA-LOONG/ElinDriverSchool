/**
 * 
 *//*

package com.elin.elinweidian.util;

import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

*/
/**
 * @author kinglone
 * 2015-8-16下午12:21:14
 *//*

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface URLMsg {
	//用来区分网络接口（URL)的那个accessId
	public int id()default 0;
	//指定URL路径
	public String path();
	//声明请求方式
	public HttpMethod httpMethod();
}
*/
