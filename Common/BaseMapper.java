package com.ncepu.feilong505.AOSystem.Common;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
public interface BaseMapper<T> {
	// 增
	@InsertProvider(type = BaseSqlProvider.class, method = "insert")
	int save(@Param("object") T bean);

	// 删
	@DeleteProvider(type = BaseSqlProvider.class, method = "delete")
	int delete(@Param("object")T bean);

	// 改(必须要有主键)
	@UpdateProvider(type = BaseSqlProvider.class, method = "update")
	int edit(@Param("object")T bean);

	// 查
	// 一条数据
	@ResultMap("BaseResultMap")
	@SelectProvider(type = BaseSqlProvider.class, method = "selectOne")
	T findOne(@Param("object")T bean);

	// 一组数据
	@ResultMap("BaseResultMap")
	@SelectProvider(type = BaseSqlProvider.class, method = "selectList")
	List<T> findList(@Param("object")T bean);

	// 默认分页(pageNum=1,pageSize=10)
	@ResultMap("BaseResultMap")
	@SelectProvider(type = BaseSqlProvider.class, method = "selectPaginationAuto")
	List<T> findListWithPageAuto(@Param("object")T bean);

	// 自定义分页
	@ResultMap("BaseResultMap")
	@SelectProvider(type = BaseSqlProvider.class, method = "selectPagination")
	List<T> findListWithPage(@Param("object")T bean, int pageNum, int pageSize);
}
