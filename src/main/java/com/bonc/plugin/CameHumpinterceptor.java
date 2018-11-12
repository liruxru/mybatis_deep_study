package com.bonc.plugin;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Intercepts(
        @Signature(
                type = ResultSetHandler.class,
                method = "handleResultSets",
                args = {Statement.class}
        )
)
public class CameHumpinterceptor implements Interceptor {

    /**
     *  使用getTarget()方法可以获取当前被拦截的对象，
     *  使用getMethod()可以获取当前被拦截的方法，
     *  使用getArgs()方法可以返回被拦截方法中的参数。
     *  通过调用invocation.proceed(); 可以执行被拦截对象真正的方法，
     *  proceed()方法实际上执行了
     *  method.invoke(target,args）方法，
     *  上面的代码中没有做任何特殊处理，直接返回了执行的结果。
     *         Object target =invocation.getTarget ();
     *         Method method =invocation.getMethod() ;
     *         Object[] args =invocation.getArgs() ;
     *         Object result =invocation.proceed ();
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        List<Object> list = (List<Object>) invocation.proceed();
        for (Object object : list) {
            if(object instanceof Map){
                processMap((Map)object);
            }else{
                break;
            }
            
        }
        return list;
    }

    private void processMap(Map map) {
        Set<Map.Entry<String, Object>> set = map.entrySet();
        for (Map.Entry<String, Object> stringObjectEntry : set) {
            String key = stringObjectEntry.getKey();
            Object value = stringObjectEntry.getValue();
            map.remove(key);
            map.put(underlineToCamelhump(key),value);
        }


    }

    private Object underlineToCamelhump(String inputString) {
        StringBuilder sb =new StringBuilder();
        boolean nextUpperCase = false ;
        for (int i= 0 ; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            if (c == '_') {
                if (sb.length() > 0) {
                    nextUpperCase = true;
                } else {
                    if (nextUpperCase) {
                        sb.append(Character.toUpperCase(c));
                        nextUpperCase = false;
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                }
            }
        }
        return sb.toString() ;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target , this) ;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
