package com.ncepu.feilong505.OASystem.Service.impl;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncepu.feilong505.OASystem.Common.ObjectMap;
import com.ncepu.feilong505.OASystem.Common.Result;
import com.ncepu.feilong505.OASystem.Dao.UserMapper;
import com.ncepu.feilong505.OASystem.Model.User;
import com.ncepu.feilong505.OASystem.Model.vo.UserVO;
import com.ncepu.feilong505.OASystem.Service.UserService;

/**
 * TODO
 * 
 * @author xtysummer1121@foxmail.com
 * @date 2019年1月16日
 */
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserMapper userMapper;

	/**
	 * TODO 用户登录
	 * 
	 * @author xtysummer1121@foxmail.com
	 * @date 2019年1月22日
	 * @param user
	 * @param result
	 * @return
	 */
	public Result login(User user, Result result) {
		if ((user.getUserId() != null || user.getEmail() != null) && user.getPassword() != null) {
			// 作为传递参数，防止前端提供过多数据而无法正常匹配
			User param = new User();
			param.setEmail(user.getEmail());
			param.setPassword(user.getPassword());
			param.setUserId(user.getUserId());

			User res = null;
			try {
				res = userMapper.findOne(param);
			} catch (Exception e) {
				result.setMsg("发生了错误");
				result.setState(100);
				e.printStackTrace();
			}
			if (res == null) {
				result.setMsg("账户或密码错误");
				result.setState(100);
			} else {
				result.setMsg("登陆成功");
				result.setState(200);
				Map<String, Object> map = new HashMap<>();
				map.put("userId", user.getUserId());
				result.setData(map);
			}
		} else {
			result.setMsg("请输入账户密码");
			result.setState(100);
		}
		return result;
	}

	/**
	 * TODO 增加用户
	 * 
	 * @author xtysummer1121@foxmail.com
	 * @date 2019年1月22日
	 * @param user
	 * @param result
	 * @return
	 */
	public Result addUser(User user, Result result) {
		if (user.getName() != null && user.getEmail() != null && user.getPassword() != null) {
			try {
				// 查找是否有相同邮箱的用户
				User userTest = new User();
				userTest.setEmail(user.getEmail());
				if (userMapper.findOne(userTest) == null) {
					if (userMapper.save(user) == 1) {
						Map<String, Object> map = new HashMap<>();
						map.put("userId", user.getUserId());
						result.setData(map);
						result.setMsg("注册成功");
						result.setState(200);
					}
				} else {
					result.setMsg("邮箱已被注册");
					result.setState(100);
				}
			} catch (Exception e) {
				result.setMsg("发生了错误");
				result.setState(100);
				e.printStackTrace();
			}
		} else {
			result.setMsg("请输入账户信息");
			result.setState(100);
		}
		return result;
	}

	/**
	 * TODO 精确查询用户
	 * 
	 * @author xtysummer1121@foxmail.com
	 * @date 2019年1月22日
	 * @param user
	 * @param result
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Result findUser(User user, Result result) {
		if (user.getUserId() != null) {
			try {
				user = userMapper.findOne(user);
			} catch (Exception e) {
				result.setMsg("发生了错误");
				e.printStackTrace();
			}
			if (user != null) {
				UserVO userVO = new UserVO(user);
				result.setData(userVO);
				result.setMsg("查询成功");
				result.setState(200);
			} else {
				result.setMsg("查无此人");
				result.setState(100);
			}

		} else {
			result.setMsg("缺少查询信息");
			result.setState(100);
		}
		return result;
	}

	/**
	 * TODO 修改用户信息
	 * 
	 * @author xtysummer1121@foxmail.com
	 * @date 2019年1月22日
	 * @param user
	 * @param result
	 * @return
	 */
	public Result updateUser(User user, Result result) {
		if (user.getUserId() != null) {
			int i = 0;
			try {
				i = userMapper.edit(user);
			} catch (Exception e) {
				result.setMsg("发生了错误");
				result.setState(100);
				e.printStackTrace();
			}
			if (i == 1) {
				result.setMsg("修改成功");
				result.setState(200);
			} else {
				result.setMsg("修改失败");
				result.setState(100);
			}
		} else {
			result.setMsg("缺少必要用户信息");
			result.setState(100);
		}
		return result;
	}

	/**
	 * TODO 修改用户信息
	 * 
	 * @author xtysummer1121@foxmail.com
	 * @date 2019年1月22日
	 * @param user
	 * @param result
	 * @return
	 */
	public Result deleteUser(User user, Result result) {
		if (user.getUserId() != null) {
			int i = 0;
			try {
				i = userMapper.delete(user);
			} catch (Exception e) {
				result.setMsg("发生了错误");
				result.setState(100);
				e.printStackTrace();
			}
			if (i == 1) {
				result.setMsg("删除成功");
				result.setState(200);
			} else {
				result.setMsg("删除失败");
				result.setState(100);
			}
		} else {
			result.setMsg("缺少必要用户信息");
			result.setState(100);
		}
		return result;
	}

	/**
	 * TODO 获得分页用户列表
	 * 
	 * @author xtysummer1121@foxmail.com
	 * @date 2019年1月22日
	 * @param user
	 * @param result
	 * @return
	 */
	public Result findUserWithPage(User user, Result result, int pageNum, int pageSize) {
		if (user == null)
			user = new User();
		List<User> users = new ArrayList<>();
		try {
			users = userMapper.findListWithPage(user, pageNum, pageSize);
		} catch (Exception e) {
			result.setMsg("发生了错误");
			result.setState(100);
			e.printStackTrace();
		}
		if (users.size() != 0) {
			try {
				List<UserVO> list = new ArrayList<>();
				for (User user2 : users) {
					UserVO userVO = new UserVO(user2);
					list.add(userVO);
				}
				result.setData(list);
				result.setMsg("查询成功");
				result.setState(200);
			} catch (Exception e) {
				result.setMsg("发生了错误");
				result.setState(100);
				e.printStackTrace();
			}
		} else {
			result.setMsg("查无结果");
			result.setState(100);
		}
		return result;
	}

}
