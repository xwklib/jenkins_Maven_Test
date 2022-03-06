package com.njtech.common.config;

import com.auth0.jwt.interfaces.Claim;
import com.njtech.common.JwtUtils;
import com.njtech.common.PassToken;
import com.njtech.entity.UserInfo;
import com.njtech.exception.CustomException;
import com.njtech.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    UserInfoService userInfoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object object) {
        // 从请求头中取出 token  这里需要和前端约定好把jwt放到请求头一个叫token的地方
        String token = request.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        // 检查是否有passToken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        } else { //默认全部检查
            // 执行认证
            if (token == null) {
                // 这里其实是登录失效,没token了
                throw new CustomException("4001", "请登录以访问数据");
            }
            // 获取 token 中的 签发对象(UserId)
            String userId = JwtUtils.getAudience(token);
            //找找看是否有这个user,因为需要检查用户是否存在
            UserInfo user = userInfoService.getById(userId);
            if (user == null) {
                throw new CustomException("4001", "用户不存在");
            }
            // 验证 token
            JwtUtils.verifyToken(token, userId);
            //获取载荷内容
            String userName = JwtUtils.getClaimByString(token, "userName").asString();
            Integer identity = JwtUtils.getClaimByString(token, "identity").asInt();
            //放入attribute以便后面调用
            request.setAttribute("userId", userId);
            request.setAttribute("userName", userName);
            request.setAttribute("identity", identity);
            return true;

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}

