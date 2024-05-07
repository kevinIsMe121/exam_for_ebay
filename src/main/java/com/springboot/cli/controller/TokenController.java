package com.springboot.cli.controller;

import com.springboot.cli.config.PasswordEncoder;
import com.springboot.cli.entity.AdminAddUser;
import com.springboot.cli.jwt.JwtUser;
import com.springboot.cli.jwt.TokenProvider;
import com.springboot.cli.response.ErrorCode;
import com.springboot.cli.response.Result;
import com.springboot.cli.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author liangQiDing
 */
@RestController
@Api("token测试服务器")
public class TokenController {
    @Autowired
    ResourceService resourceService;
    /**
     * 模拟数据库数据 账号 admin  密码 123456
     */
    private final static HashMap<String, String> USER = new HashMap<>() {
        {
            put("admin", "e10adc3949ba59abbe56e057f20f883e");
            put("user", "111");
        }
    };

    /**
     * @param username 用户名
     * @param password 密码
     * @return
     * @description 为了得到token
     */
    @GetMapping("/login")
    @ApiOperation("登陆示例（账号admin,密码123456）")
    public String login(String username, String password) {
        if (PasswordEncoder.matches(password, USER.get(username))) {
            // 模拟一个用户的数据 用户id为1  登录端为网页web  角色是admin
            return TokenProvider.createToken("1", "web", "admin");
        }
        //此为简化版，除了admin账号，其他账号默认密码怎么输入都算正确，并且附上user的角色值
        else {
            return TokenProvider.createToken("1", "web", "user");
        }

    }

    /**
     * @param token
     * @return
     * @description 校验刚刚得到的token
     */
    @GetMapping("/token/validate")
    @ApiOperation("token校验")
    public JwtUser tokenValidate(String token) {
        return TokenProvider.checkToken(token);
    }

    /**
     * @param adminAddUser
     * @return
     * @description admin用户才可以添加关联resource
     */
    @PostMapping("/admin/addUser")
    public Result addUser(@RequestBody AdminAddUser adminAddUser) {

        boolean res = resourceService.addResource(adminAddUser);
        return new Result().success(res);
    }

    /**
     * @param user
     * @return
     * @description 所有用户皆可看到是否有resource的权限
     */
    @PostMapping("/findAuth")
    public Result addUser(@RequestBody JwtUser user) {
        boolean res1 = resourceService.findAuth(user, "resA");
        if (!res1) {
            return new Result(ErrorCode.NO_PERMISSION);
        } else {
            return new Result().success(res1);
        }
    }
}
