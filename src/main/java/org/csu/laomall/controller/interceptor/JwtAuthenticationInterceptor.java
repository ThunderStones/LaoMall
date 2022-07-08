package org.csu.laomall.controller.interceptor;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.csu.laomall.anotation.PassToken;
import org.csu.laomall.common.CommonResponse;
import org.csu.laomall.common.ResponseCode;
import org.csu.laomall.service.UserService;
import org.csu.laomall.util.JWTUtil;
import org.csu.laomall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(PassToken.class)) {
            if (method.getAnnotation(PassToken.class).required()) {
                return true;
            }
        }

        if (token == null) {
            System.out.println("token is null");
            generateResponse("没有在header中找到Authorization字段，请在头部加入Authorization字段并将值设为登陆后返回的token。(token is null)", response);
            return false;
        }
        System.out.println(token.replace("Bearer ", ""));
        String username;
        try {
            username = JWTUtil.getUsername(token.replace("Bearer ", ""));
        } catch (TokenExpiredException e) {
            response.getWriter().write(new ObjectMapper().writeValueAsString(
                    CommonResponse.createForError(ResponseCode.NEED_LOGIN.getCode(), "Token Expired"))
            );
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        UserVO user = userService.getUserInfo(username);
        if (user == null) {
            generateResponse("token is invalid", response);
            return false;
        }

        request.setAttribute("user", user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    private void generateResponse(String msg, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=utf-8");
        CommonResponse<String> commonResponse = CommonResponse.createForError(msg);
        response.getWriter().write(new ObjectMapper().writeValueAsString(commonResponse));
    }
}
