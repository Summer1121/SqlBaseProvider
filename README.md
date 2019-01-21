# SqlBaseProvider
对于springboot-mybatis单表操作的封装
包含单表的
  -增添
  -删除
  -修改
  -查询
    -单条数据
    -多条数据
    -分页多条查询

（一）
将Common下
  -BaseMapper.java
  -BaseMapperInterceptor.java
  -BaseSqlProvider.java
  -Table.java
四个文件放入conmmon下，修改部分信息来适配自己的项目

（二）
对于某个XXXmapper.xml文件
必须要有<BaseResultMap>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.ncepu.feilong505.AOSystem.Dao.UserMapper">
	<resultMap id="BaseResultMap" type="com.ncepu.feilong505.AOSystem.Model.User">
		<id column="user_id" property="userId"/>
		<result column="password" property="password"/>
		<result column="name" property="name"/>
		<result column="position" property="position"/>
		<result column="work_station_id" property="workStationId"/>
		<result column="phone" property="phone"/>
		<result column="email" property="email"/>
		<result column="role" property="role"/>
		<result column="icon" property="icon"/>
	</resultMap>
</mapper>
  
（三）
在对应的XXXmapper.java中
@Mapper
public interface UserMapper extends BaseMapper<User> {}
  
（四）
在对应的server中调用XXXmapper中的方法吧~
