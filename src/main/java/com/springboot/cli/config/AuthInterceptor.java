package com.springboot.cli.config;

import com.springboot.cli.exception.ExamException;
import com.springboot.cli.jwt.AuthStorage;
import com.springboot.cli.jwt.JwtUser;
import com.springboot.cli.jwt.TokenProvider;
import com.springboot.cli.response.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 *
 * @author lqd
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("过滤器");

        String token = request.getHeader(AuthStorage.TOKEN_KEY);
        if (StringUtils.hasLength(token)) {
            JwtUser jwtUser = TokenProvider.checkToken(token);
            //此处完成如果用户不是admin就抛异常的需求二
            if(!jwtUser.getRole().equals("admin")){
                throw new ExamException(ErrorCode.NO_PERMISSION);
            }
            // 是否认证通过
            if (jwtUser.isValid()) {
                // 保存授权信息
                AuthStorage.setUser(token, jwtUser);
                return true;
            }
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("请先登录！");
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求完成清除授权信息
        AuthStorage.clearUser();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
