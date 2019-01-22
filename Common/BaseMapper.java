package com.ncepu.feilong505.AOSystem.Common;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
public interface BaseMapper<T> {
	// 增
	@Options(useGeneratedKeys=true)
	@InsertProvider(type = BaseSqlProvider.class, method = "insert")
	int save(T bean);

	// 删
	@DeleteProvider(type = BaseSqlProvider.class, method = "delete")
	int delete(T bean);

	// 改(必须要有主键)
	@UpdateProvider(type = BaseSqlProvider.class, method = "update")
	int edit(T bean);

	// 查
	// 一条数据
	@ResultMap("BaseResultMap")
	@SelectProvider(type = BaseSqlProvider.class, method = "selectOne")
	T findOne(T bean);

	// 一组数据
	@ResultMap("BaseResultMap")
	@SelectProvider(type = BaseSqlProvider.class, method = "selectList")
	List<T> findList(T bean);

	// 默认分页(pageNum=1,pageSize=10)
	@ResultMap("BaseResultMap")
	@SelectProvider(type = BaseSqlProvider.class, method = "selectPaginationAuto")
	List<T> findListWithPageAuto(T bean);

	// 自定义分页
	@ResultMap("BaseResultMap")
	@SelectProvider(type = BaseSqlProvider.class, method = "selectPagination")
	List<T> findListWithPage(@Param("object")T bean, int pageNum, int pageSize);
}
