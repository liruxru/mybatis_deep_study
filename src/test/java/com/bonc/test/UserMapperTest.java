package com.bonc.test;

import com.bonc.mapper.UserMapper;
import com.bonc.pojo.SysUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapperTest {

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
    public void testSelectByUser(){
        SqlSession sqlSession = getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        SysUser user = new SysUser();
        user.setUserName("admin");
        List<SysUser> sysUsers = mapper.selectByUser(user);

        Assert.assertNotNull(sysUsers);
        Assert.assertEquals(1,sysUsers.size());

        user.setUserEmail("admin@mybatis.tk1");
        List<SysUser> sysUsers1 = mapper.selectByUser(user);

        Assert.assertNotNull(sysUsers);
        Assert.assertEquals(0,sysUsers1.size());
        sqlSession.close();
    }


    @Test
    public void updateByIdSelective(){
        SqlSession sqlSession = getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);


        SysUser sysUser = mapper.selectById(1L);
        sysUser.setUserEmail("5555555");

        int i = mapper.updateByIdSelective(sysUser);
        Assert.assertEquals(1,i);


        sqlSession.rollback();
        sqlSession.close();
    }


    @Test
    public void selectByIdOrUserName(){
        SqlSession sqlSession = getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        SysUser user = new SysUser();
        user.setId(1L);
        SysUser user1 = mapper.selectByIdOrUserName(user);
        Assert.assertNotNull(user1);

        user.setUserName("admin");
        SysUser user2 = mapper.selectByIdOrUserName(user);
        Assert.assertNotNull(user2);

        SysUser user3 = new SysUser();
        SysUser user4 = mapper.selectByIdOrUserName(user3);
        Assert.assertNull(user4);

        sqlSession.close();
    }

    @Test
    public void selectByIdList(){
        SqlSession sqlSession = getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        idList.add(1001L);
        List<SysUser> sysUsers = mapper.selectByIdList(idList);
        Assert.assertNotNull(sysUsers);
        Assert.assertNotEquals(0,sysUsers.size());

        List<SysUser> sysUsers3 = mapper.selectByIdList1(idList);
        Assert.assertNotNull(sysUsers3);
        Assert.assertNotEquals(0,sysUsers3.size());

        Long[] ids = new Long[]{1L,1001L};
        List<SysUser> sysUsers1 = mapper.selectByIds(ids);

        Assert.assertNotNull(sysUsers1);
        Assert.assertNotEquals(0,sysUsers1.size());

        sqlSession.close();
    }

    @Test
    public void  insertBathTest(){
        SqlSession sqlSession = getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<SysUser> sysUsers = new ArrayList<>();

        for (int i=0;i<3;i++){
            SysUser user = new SysUser();
            user.setUserName("test"+i);
            sysUsers.add(user);
        }

        int i = mapper.insertList(sysUsers);
        Assert.assertEquals(3,i);

        sqlSession.rollback();
        sqlSession.close();
    }
    @Test
    public void  updateByMap(){
        SqlSession sqlSession = getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",1L);
        paramMap.put("user_name","test");
        paramMap.put("user_email","test@email");
        int i = mapper.updateByMap(paramMap);
        Assert.assertEquals(1,i);


        sqlSession.rollback();
        sqlSession.close();
    }

    @Test
    public void  selectUserNameLike(){
        SqlSession sqlSession = getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<SysUser> sysUsers = mapper.selectByNameLike("min");
        System.out.println(sysUsers);
        sqlSession.close();
    }
}
