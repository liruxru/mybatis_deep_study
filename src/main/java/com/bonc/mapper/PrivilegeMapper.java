package com.bonc.mapper;

import com.bonc.pojo.SysPrivilege;
import org.apache.ibatis.annotations.SelectProvider;

public interface PrivilegeMapper {
    @SelectProvider(type=PrivilegeProvider.class,method = "selectById")
    SysPrivilege selectById(Long id);
}
