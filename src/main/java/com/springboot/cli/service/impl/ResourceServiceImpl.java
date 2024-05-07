package com.springboot.cli.service.impl;

import com.alibaba.fastjson.JSON;
import com.springboot.cli.entity.AdminAddUser;
import com.springboot.cli.jwt.JwtUser;
import com.springboot.cli.service.ResourceService;
import com.springboot.cli.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {
    /**
     * @param adminAddUser 带有新增资源的完整信息
     * @return
     * @description 需求二：新增资源存入数据库
     * @author: kaiyu.wang
     */
    @Override
    public boolean addResource(AdminAddUser adminAddUser) {
        List<String> resourceList = adminAddUser.getEndpoint();
        Long userId = adminAddUser.getUserId();
        String filePath = System.getProperty("user.dir") + File.separator + "userResource" + File.separator + userId + ".json";
        try {
            handleRepeatResource(filePath, resourceList);
            FileUtil.writeFile(filePath, JSON.toJSONString(resourceList));
        } catch (IOException e) {
            log.error("add resource error", e);
            return false;
        }
        return true;
    }

    /**
     * @param filePath     存入路径
     * @param resourceList
     * @description 处理数据库重复的资源
     * @author kaiyu.wang
     */
    private void handleRepeatResource(String filePath, List<String> resourceList) throws IOException {
        String content = FileUtil.readFile(filePath);
        if (StringUtils.isEmpty(content)) {
            return;
        }
        List<String> originResourceList = JSON.parseArray(content, String.class);
        for (String resource : originResourceList) {
            if (!resourceList.contains(resource)) {
                resourceList.add(resource);
            }
        }
    }

    /**
     * @param userInfo 用户信息
     * @param resource 资源名
     * @return
     * @description 需求三：查询用户是否有该资源的权限
     * @author kaiyu.wang
     */
    @Override
    public boolean findAuth(JwtUser userInfo, String resource) {
        String filePath = System.getProperty("user.dir") + File.separator + "userResource" + File.separator + userInfo.getId() + ".json";
        String content = null;
        try {
            content = FileUtil.readFile(filePath);
        } catch (IOException e) {
            log.error("read file error", e);
        }
        if (StringUtils.isEmpty(content)) {
            return false;
        }
        List<String> resourceList = JSON.parseArray(content, String.class);
        return resourceList.contains(resource);
    }
}

