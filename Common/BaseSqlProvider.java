/**   
* @Title: BaseSqlProvider.java 
* @Package com.ncepu.feilong505.AOSystem.Common 
* @Description: TODO 为mapper提供动态的sql语句
* @author xtysummer1121@foxmail.com
* @date 2019年1月5日 下午6:39:10 
* @version V1.0   
*/

package com.ncepu.feilong505.AOSystem.Common;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;

/**
 * @ClassName: BaseSqlProvider
 * @Description: TODO 为mapper提供动态的sql语句
 * @author xtysummer@foxmail.com
 * @date 2019年1月5日 下午6:39:10
 */

public class BaseSqlProvider {
	public BaseSqlProvider() {
		init();
	}

	Class<?> beanClass;// javaBean

	private ResultMapping idResult;// 主键映射

	private static MappedStatement mappedStmt;// mapper的映射对象

	private static Properties properties;// properties配置

	private Map<String, String> baseResultMapping;// 基本映射

	public static void setMappedStmt(MappedStatement mappedStmt) {
		BaseSqlProvider.mappedStmt = mappedStmt;
	}

	public static void setProperties(Properties properties) {
		BaseSqlProvider.properties = properties;
	}

	
	
	// 初始化
	private void init() {
		//当存在多个mapper也就存在多个basesqlmapper，会“模棱两可”问题，
		//需要指定BaseResultMap的完整路径
		String id = mappedStmt.getId();
		if(id!=null)
		{
			int index=id.lastIndexOf(".");
			if(index!=-1)
			{
				String prefix=id.substring(0, index);
				String resultMapId=prefix+".BaseResultMap";
				ResultMap resultMap = mappedStmt.getConfiguration().getResultMap(resultMapId);
				//主键
				idResult = resultMap.getIdResultMappings().get(0);
				parse(resultMap);
			}
		}
	}

	// 解析resultMap
	private void parse(ResultMap resultMap) {
		baseResultMapping = new HashMap<>();
		for (ResultMapping result : resultMap.getResultMappings()) {
			baseResultMapping.put(result.getProperty(), result.getColumn());
		}
	}

	//控制台输出sql
	private void sqlDebug(SQL sql) {
		System.out.println(sql.toString());
		return;
	}

	//插入一个数据
	public String insert(Object object) {
		if (object == null)
			throw new RuntimeException("The target can't be Null.");
		else {
			SQL sql = new SQL();
			try {
				// TABLE
				Class<?> clazz = object.getClass();
				Table table = (Table) clazz.getAnnotation(Table.class);
				String tableName = table.value();
				// INSERT
				sql.INSERT_INTO(tableName);
				// VALUES
				for (Map.Entry<String, String> pair : baseResultMapping.entrySet()) {
					String property = pair.getKey();
					String column = pair.getValue();
					Field field = clazz.getDeclaredField(property);
					field.setAccessible(true);
					Object value = field.get(object);
					if (value != null) {
						sql.VALUES(String.format("`%s`", column), String.format("#{%s}", property));
					}
				}
				sqlDebug(sql);

			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return sql.toString();
		}
	}

	//删除一个数据
	public String delete(Object object) {
		if (object == null)
			throw new RuntimeException("The target can't be Null.");
		else {
			SQL sql = new SQL();
			try {
				// TABLE
				Class<?> clazz = object.getClass();
				Table table = (Table) clazz.getAnnotation(Table.class);
				String tableName = table.value();
				// DELETE
				sql.DELETE_FROM(tableName);
				// WHERE
				for (Map.Entry<String, String> pair : baseResultMapping.entrySet()) {
					String property = pair.getKey();
					String column = pair.getValue();
					Field field = clazz.getDeclaredField(property);
					field.setAccessible(true);
					Object value = field.get(object);
					if (value != null) {
						sql.WHERE(String.format("`%s` = #{%s}", column, property));
					}
				}
				sqlDebug(sql);

			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return sql.toString();
		}

	}

	//更新一个数据
	public String update(Object object) {
		if (object == null)
			throw new RuntimeException("The target can't be Null.");
		else {
			SQL sql = new SQL();
			try {
				// TABLE
				Class<?> clazz = object.getClass();
				Table table = (Table) clazz.getAnnotation(Table.class);
				String tableName = table.value();
				// UPDATE
				sql.UPDATE(tableName);
				// SET
				for (Map.Entry<String, String> pair : baseResultMapping.entrySet()) {
					String property = pair.getKey();
					String column = pair.getValue();
					Field field = clazz.getDeclaredField(property);
					field.setAccessible(true);
					Object value = field.get(object);
					if (value != null) {
						sql.SET(String.format("`%s`= #{%s}", column, property));
					}
				}
				// WHERE
				{
					String property = idResult.getProperty();
					String column = idResult.getColumn();
					Field field = clazz.getDeclaredField(property);
					field.setAccessible(true);
					Object value = field.get(object);
					if (value == null) {
						throw new RuntimeException("method update need primary key");
					}
					sql.WHERE(String.format("`%s` = #{%s} ", column, property));
				}
				sqlDebug(sql);

			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return sql.toString();
		}
	}

	//查找一组数据
	public String selectList(Object object) {
		if (object == null)
			throw new RuntimeException("The target can't be Null.");
		else {
			SQL sql = new SQL();
			try {
				// TABLE
				Class<?> clazz = object.getClass();
				Table table = (Table) clazz.getAnnotation(Table.class);
				String tableName = table.value();
				// SELECT
				sql.SELECT("*");
				sql.FROM(tableName);
				// WHERE
				for (Map.Entry<String, String> pair : baseResultMapping.entrySet()) {
					String property = pair.getKey();
					String column = pair.getValue();
					Field field = clazz.getDeclaredField(property);
					field.setAccessible(true);
					Object value = field.get(object);
					if (value != null) {
						sql.WHERE(String.format("`%s`= #{%s}", column, property));
					}
				}
				sqlDebug(sql);

			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return sql.toString();
		}
	}

	//查找一条数据
	public String selectOne(Object object) {
		return selectList(object);
	}

	//默认分页查询
	public String selectPaginationAuto(Object object) {
		return selectPagination(object, 1, 10);
	}
	
	//自定义的分页查询
	public String selectPagination(Object object, int pageNum, int pageSize) {
		String sqlOld = selectList(object);
		StringBuilder sql=new StringBuilder(sqlOld.toString());
		String limit=String.format(" LIMIT %s, %s", (pageNum-1)*pageSize, pageSize);
		sql.append(limit);
		System.out.println(limit);
		return sql.toString();
	}
}
