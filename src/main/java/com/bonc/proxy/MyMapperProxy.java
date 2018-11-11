package com.bonc.proxy;

import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class MyMapperProxy<T> implements InvocationHandler {
    private Class<T> mapperInterface;
    private SqlSession sqlSession;

    public MyMapperProxy(Class<T> mapperInterface, SqlSession sqlSession) {
        this.mapperInterface = mapperInterface;
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<T> list = sqlSession.selectList(
                mapperInterface.getCanonicalName()+"."+method.getName(),args);
        return list;
    }


}
