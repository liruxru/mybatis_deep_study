package com.bonc.mapper;

import com.bonc.pojo.SysRole;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


public interface RoleMapper {
    @Results(
            id="roleResultMap",
            value={
                    @Result(property = "id",column = "id",id=true),
                    @Result(property = "roleName",column = "role_name"),
                    @Result(property = "enable",column = "enable"),
                    @Result(property = "createBy",column = "create_by"),
                    @Result(property = "createTime",column = "create_time",jdbcType = JdbcType.TIMESTAMP )
            }
    )
    @Select("select * from sys_role where id = #{id}")
    SysRole selectById(Long id);

    @ResultMap("roleResultMap")
    @Select("select * from sys_role")
    List<SysRole> selectAll();

    @Insert("insert into sys_role " +
            "values(#{roleName},#{enable},#{createBy},#{createTime,jdbcType=TIMESTAMP})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insert(SysRole sysRole);

    @Insert("insert into sys_role " +
            "values(#{roleName},#{enable},#{createBy},#{createTime,jdbcType=TIMESTAMP})")
    @SelectKey(statement = "select last_insert_id()",
    keyProperty = "id",resultType = Long.class,before = false)
    int insert2(SysRole sysRole);

    @Update("update sys_role set " +
            "role_name=#{roleName}, " +
            "enable=#{enable}, " +
            "create_by=#{createBy}, " +
            "create_time=#{createTime,jdbcType=TIMESTAMP} " +
            "where id=#{id}")
    int updateById(SysRole sysRole);

    @Delete("delete from sys_role where id = #{id}")
    int delete(Long id);
}
