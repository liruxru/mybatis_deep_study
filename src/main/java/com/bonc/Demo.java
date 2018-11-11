package com.bonc;

import com.bonc.mapper.PrivilegeMapper;
import com.bonc.mapper.RoleMapper;
import com.bonc.mapper.UserMapper;
import com.bonc.pojo.Country;
import com.bonc.pojo.SysPrivilege;
import com.bonc.pojo.SysRole;
import com.bonc.pojo.SysUser;
import com.bonc.proxy.MyMapperProxy;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.util.Date;
import java.util.List;

public class Demo {
    public  static SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory =
                new SqlSessionFactoryBuilder().build(inputStream);
    }

    private SqlSession getSqlSession(){
        SqlSession session = sqlSessionFactory.openSession();
        return  session;
    }


    @Test
    public void test() throws IOException {

        SqlSession session =getSqlSession();
        try {
            List<Country> blog = session.selectList(
                    "com.bonc.mapper.CountryMapper.selectAll", 101);
            System.out.println(blog);
        } finally {
            session.close();
        }
    }
    @Test
    public void testSelectById() throws IOException {
        SqlSession session =getSqlSession();

        UserMapper userMapper = session.getMapper(UserMapper.class);

        SysUser sysUser = userMapper.selectById(1L);
        Assert.assertNotNull(sysUser);
        Assert.assertEquals("admin",sysUser.getUserName());
        System.out.println(sysUser);
        session.close();
    }

    @Test
    public void testSelectAll() throws IOException {
        SqlSession session =getSqlSession();

        UserMapper userMapper = session.getMapper(UserMapper.class);

        List<SysUser> sysUsers = userMapper.selectAll();
        Assert.assertNotNull(sysUsers);
        Assert.assertTrue(sysUsers.size()>0);
        System.out.println(sysUsers);
        session.close();
    }

    @Test
    public void testSelectRoleByUserId() throws IOException {
        SqlSession session =getSqlSession();

        UserMapper userMapper = session.getMapper(UserMapper.class);

        List<SysRole> sysRoles = userMapper.selectRolesByUserId(1L);
        Assert.assertNotNull(sysRoles);
        Assert.assertTrue(sysRoles.size()>0);
        System.out.println(sysRoles);
        session.close();
    }

    @Test
    public void testInsert() throws IOException {
        SqlSession session =getSqlSession();

        try{
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserEmail("abc@qq.com");
            user.setCreateTime(new Date());
            user.setHeadImg(new byte[]{1,2,3});
            user.setUserPassword("123456");
            user.setUserInfo(null);
            UserMapper userMapper = session.getMapper(UserMapper.class);
            int result = userMapper.insert(user);

            Assert.assertEquals(1,result);
            Assert.assertNull(user.getId());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.rollback();
            session.close();
        }


    }
    @Test
    public void testInsert2() throws IOException {
        SqlSession session =getSqlSession();

        try{
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserEmail("abc@qq.com");
            user.setCreateTime(new Date());
            user.setHeadImg(new byte[]{1,2,3});
            user.setUserPassword("123456");
            user.setUserInfo(null);
            UserMapper userMapper = session.getMapper(UserMapper.class);
            int result = userMapper.insertAndReturnKey(user);

            Assert.assertEquals(1,result);
            Assert.assertNotNull(user.getId());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.rollback();
            session.close();
        }


    }
    @Test
    public void testInsert3() throws IOException {
        SqlSession session =getSqlSession();

        try{
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserEmail("abc@qq.com");
            user.setCreateTime(new Date());
            user.setHeadImg(new byte[]{1,2,3});
            user.setUserPassword("123456");
            user.setUserInfo(null);
            UserMapper userMapper = session.getMapper(UserMapper.class);
            int result = userMapper.insert3(user);

            Assert.assertEquals(1,result);
            Assert.assertNotNull(user.getId());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.rollback();
            session.close();
        }


    }

    @Test
    public void testUpdate() throws IOException {
        SqlSession session =getSqlSession();

        try{

            UserMapper userMapper = session.getMapper(UserMapper.class);
            SysUser user = userMapper.selectById(1L);
            Assert.assertEquals("admin",user.getUserName());
            user.setUserName("adminNew");
            int result = userMapper.update(user);
            SysUser user1 = userMapper.selectById(1L);
            Assert.assertEquals("adminNew",user1.getUserName());
            Assert.assertNotNull(user.getId());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.rollback();
            session.close();
        }


    }

    @Test
    public void testdelete() throws IOException {
        SqlSession session =getSqlSession();

        try{
            UserMapper userMapper = session.getMapper(UserMapper.class);
            int result = userMapper.delete(1L);
            Assert.assertEquals(1,result);
            SysUser user = userMapper.selectById(1L);

            Assert.assertNull(user);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.rollback();
            session.close();
        }


    }
    @Test
    public void testselectRolesByUserIdAndRoleEnabled() throws IOException {
        SqlSession session =getSqlSession();

        try{

            UserMapper userMapper = session.getMapper(UserMapper.class);
            List<SysRole> sysRoles = userMapper.selectRolesByUserIdAndRoleEnabled(1L, true);
            System.out.println(sysRoles);
            Assert.assertNotNull(sysRoles);
            Assert.assertTrue(sysRoles.size()>0);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.rollback();
            session.close();
        }


    }

    /**
     *
     */

    @Test
    public void testProxy(){
        SqlSession sqlSession = getSqlSession();
        MyMapperProxy proxy = new MyMapperProxy(UserMapper.class,sqlSession);
        UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{UserMapper.class},
                proxy
        );
        List<SysUser> sysUsers = userMapper.selectAll();
        Assert.assertNotNull(sysUsers);
        Assert.assertNotEquals(sysUsers.size(),0);
        sqlSession.close();

    }

    /**
     * 注解测试
     */
    @Test
    public void annotationTestSelect(){
        SqlSession sqlSession = getSqlSession();
        RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
        SysRole sysRole = roleMapper.selectById(1L);
        Assert.assertNotNull(sysRole);
        PrivilegeMapper privilegeMapper = sqlSession.getMapper(PrivilegeMapper.class);
        SysPrivilege sysPrivilege = privilegeMapper.selectById(1L);
        Assert.assertNotNull(sysPrivilege);
        sqlSession.close();
    }
    @Test
    public void annotationTestSelectAll(){
        SqlSession sqlSession = getSqlSession();
        RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
        List<SysRole> sysRoles = roleMapper.selectAll();
        Assert.assertNotNull(sysRoles);
        Assert.assertNotEquals(0,sysRoles.size());
        sqlSession.close();
    }
    @Test
    public void annotationTestUpdate(){
        SqlSession sqlSession = getSqlSession();
        RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
        SysRole sysRole = roleMapper.selectById(1L);
        Assert.assertEquals("管理员",sysRole.getRoleName());
        sysRole.setRoleName("test");

        roleMapper.updateById(sysRole);
        SysRole sysRole1 = roleMapper.selectById(1L);
        Assert.assertEquals("test",sysRole1.getRoleName());
        sqlSession.rollback();
        sqlSession.close();
    }

    @Test
    public void annotationTestDelete(){
        SqlSession sqlSession = getSqlSession();
        RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
        int delete = roleMapper.delete(1L);
        Assert.assertEquals(1,delete);
        sqlSession.rollback();
        sqlSession.close();
    }
}
