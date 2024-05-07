package com.springboot.cli;

import com.springboot.cli.controller.TokenController;
import com.springboot.cli.entity.AdminAddUser;
import com.springboot.cli.exception.ExamException;
import com.springboot.cli.jwt.JwtUser;
import com.springboot.cli.response.ErrorCode;
import com.springboot.cli.service.ResourceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class JwtDomeApplicationTests {
    @Autowired
    TokenController tokenController;

    @Test
    void contextLoads() {
    }

    @Test
    public void testLogin() {

        String str = tokenController.login("admin", "123456");
        System.out.println(str);
    }

    @Autowired
    private ResourceService resourceService;

    @Test
    public void testAddResource() {
        // 新增资源信息
        List<String> resourceList = new ArrayList<>();
        resourceList.add("resA");
        resourceList.add("resB");
        AdminAddUser adminAddResource = new AdminAddUser()
                .setUserId(123456L)
                .setEndpoint(resourceList);
        boolean res = resourceService.addResource(adminAddResource);
        if(res){
            System.out.println("新增成功！");
        }

    }
    @Test
    public void testFindAuth() {
        // 查询资源信息
        JwtUser user = new JwtUser()
                .setId(123456L)
                .setAccountName("Marry")
                .setRole("user");
        boolean res1 = resourceService.findAuth(user, "resA");
        if (!res1) {
            throw new ExamException(ErrorCode.NO_PERMISSION);
        }
        //此为无权限的资源
        boolean res2 = resourceService.findAuth(user, "resD");
        if (!res2) {
            throw new ExamException(ErrorCode.NO_PERMISSION);

        }
    }
}
