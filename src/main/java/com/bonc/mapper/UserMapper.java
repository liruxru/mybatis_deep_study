package com.bonc.mapper;

import com.bonc.pojo.SysRole;
import com.bonc.pojo.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    SysUser selectById(Long id);

    /**
     * 查找所有用户
     * @return
     */
    List<SysUser> selectAll();

    /**
     * 通过用户id查找所有角色
     * @param id
     * @return
     */
    List<SysRole> selectRolesByUserId(Long id);

    int insert(SysUser user);

    /**
     * 插入返回主键
     * @param user
     * @return
     */
    int insertAndReturnKey(SysUser user);

    /**
     * 插入返回主键
     * @param user
     * @return
     */
    int insert3(SysUser user);

    int update(SysUser user);

    int delete(long id);

    /**
     * 查找用户及可用角色
     * @param userId
     * @param enable
     * @return
     */
    List<SysRole> selectRolesByUserIdAndRoleEnabled(@Param("userId") Long userId,@Param("enable") Boolean enable);

    List<SysUser> selectByUser(SysUser sysUser);

    /**
     * 动态更新user
     * @param sysUser
     * @return
     */
    int updateByIdSelective(SysUser sysUser);

    /**
     * 通过id或者username查询
     * @param sysUser
     * @return
     */
    SysUser selectByIdOrUserName(SysUser sysUser);

    /**
     * 批量查询
     * @param idList
     * @return
     */
    List<SysUser> selectByIdList(List<Long> idList);
    List<SysUser> selectByIds(Long[] idArray);
    // 指定集合名称
    List<SysUser> selectByIdList1(@Param("idList") List<Long> idList);

    /**
     * 批量插入
     * @param sysUsers
     * @return
     */
    int insertList(List<SysUser> sysUsers);

    int updateByMap(Map<String,Object> paramMap);

    List<SysUser> selectByNameLike(@Param("userName") String userName);
}
