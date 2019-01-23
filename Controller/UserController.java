package com.ncepu.feilong505.OASystem.Controller;

import org.springframework.asm.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ncepu.feilong505.OASystem.Common.PageBean;
import com.ncepu.feilong505.OASystem.Common.Result;
import com.ncepu.feilong505.OASystem.Groups.ADD;
import com.ncepu.feilong505.OASystem.Groups.DELETE;
import com.ncepu.feilong505.OASystem.Groups.EDIT;
import com.ncepu.feilong505.OASystem.Model.User;
import com.ncepu.feilong505.OASystem.Service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;

	/**
	 * TODO 用户登录
	 * 
	 * @author xtysummer1121@foxmail.com
	 * @date 2019年1月22日
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "/Login", method = { RequestMethod.GET })
	public JSONObject login(@RequestBody User user) {
		Result result = new Result();
		userService.login(user, result);
		return (JSONObject) JSON.toJSON(result);
	}

	/**
	 * TODO 增加用户
	 * 
	 * @author xtysummer1121@foxmail.com
	 * @date 2019年1月22日
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "/User", method = { RequestMethod.POST })
	public JSONObject addUser(@RequestBody @Validated(ADD.class) User user) {
		Result result = new Result();
		userService.addUser(user, result);
		return (JSONObject) JSON.toJSON(result);
	}

	/**
	 * TODO 获取用户信息
	 * 
	 * @author xtysummer1121@foxmail.com
	 * @date 2019年1月22日
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "/User", method = { RequestMethod.GET })
	public JSONObject getUser(@RequestBody User user) {
		Result result = new Result();
		userService.findUser(user, result);
		return (JSONObject) JSON.toJSON(result);
	}

	/**
	 * TODO 删除用户
	 * 
	 * @author xtysummer1121@foxmail.com
	 * @date 2019年1月22日
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "/User", method = { RequestMethod.DELETE })
	public JSONObject deleteUser(@RequestBody @Validated({ DELETE.class }) User user) {
		Result result = new Result();
		userService.deleteUser(user, result);
		return (JSONObject) JSON.toJSON(result);
	}

	/**
	 * TODO 修改用户信息
	 * 
	 * @author xtysummer1121@foxmail.com
	 * @date 2019年1月22日
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "/User", method = { RequestMethod.PUT })
	public JSONObject editUser(@RequestBody @Validated(EDIT.class) User user) {
		Result result = new Result();
		userService.updateUser(user, result);
		return (JSONObject) JSON.toJSON(result);
	}
	
	/**
	 * TODO 分页获取用户信息
	 * 
	 * @author xtysummer1121@foxmail.com
	 * @date 2019年1月22日
	 * @param user,pageNum,pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "/UserPage", method = { RequestMethod.GET })
	public JSONObject getUserWithPage(@RequestBody PageBean<User>  bean) {
		Result result = new Result();
		userService.findUserWithPage((User)bean.getBean(), result, bean.getPageNum(),bean.getPageSize());
		return (JSONObject) JSON.toJSON(result);
	}
}
