package com.ncepu.feilong505.OASystem.Service;

import org.springframework.stereotype.Service;

import com.ncepu.feilong505.OASystem.Common.Result;
import com.ncepu.feilong505.OASystem.Model.User;

/**
 * TODO
 * 
 * @author xtysummer1121@foxmail.com
 * @date 2019年1月16日
 */
@Service
public interface UserService {

	/**
	 * TODO 用户登录
	 * 
	 * @author xtysummer1121@foxmail.com
	 * @date 2019年1月22日
	 * @param user
	 * @param result
	 * @return
	 */
	public Result login(User user, Result result);

	/**
	 * TODO 增加用户
	 * 
	 * @author xtysummer1121@foxmail.com
	 * @date 2019年1月22日
	 * @param user
	 * @param result
	 * @return
	 */
	public Result addUser(User user, Result result);

	/**
	 * TODO 精确查询用户
	 * 
	 * @author xtysummer1121@foxmail.com
	 * @date 2019年1月22日
	 * @param user
	 * @param result
	 * @return
	 */
	public Result findUser(User user, Result result);

	/**
	 * TODO 修改用户信息
	 * 
	 * @author xtysummer1121@foxmail.com
	 * @date 2019年1月22日
	 * @param user
	 * @param result
	 * @return
	 */
	public Result updateUser(User user, Result result);

	/**
	 * TODO 修改用户信息
	 * 
	 * @author xtysummer1121@foxmail.com
	 * @date 2019年1月22日
	 * @param user
	 * @param result
	 * @return
	 */
	public Result deleteUser(User user, Result result);

	/**
	 * TODO 获得分页用户列表
	 * 
	 * @author xtysummer1121@foxmail.com
	 * @date 2019年1月22日
	 * @param user
	 * @param result
	 * @return
	 */
	public Result findUserWithPage(User user, Result result, int pageNum, int pageSize);

}
