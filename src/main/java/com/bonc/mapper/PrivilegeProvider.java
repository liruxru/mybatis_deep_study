package com.bonc.mapper;

import org.apache.ibatis.jdbc.SQL;

public class PrivilegeProvider {

    public String selectById(final Long id){
        SQL sql = new SQL();
        sql.SELECT("id,privilege_name,privilege_url")
                .FROM("sys_privilege")
                .WHERE("id=#{id}");
        return sql.toString();
    }
}
