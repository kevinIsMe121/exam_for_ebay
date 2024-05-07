package com.springboot.cli.service;


import com.springboot.cli.entity.AdminAddUser;
import com.springboot.cli.jwt.JwtUser;

public interface ResourceService {

    /**
     * @Description: 新增资源
     *
     * @author:王楷宇
     * @return
     */
    public boolean addResource(AdminAddUser adminAddUser);

    /**
     * @description 需求三：查询用户是否有该资源的权限
     *
     * @author kaiyu.wang
     * @param userInfo  用户信息
     * @param resource 资源名
     * @return
     */
    public boolean findAuth(JwtUser userInfo, String resource);
}
