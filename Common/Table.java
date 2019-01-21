package com.ncepu.feilong505.AOSystem.Common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO 作为pojo与数据库表的映射
 * @author xtysummer1121@foxmail.com
 * @time 2019年1月7日
 */

//作用域
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)

public @interface Table {
	String value();
}
