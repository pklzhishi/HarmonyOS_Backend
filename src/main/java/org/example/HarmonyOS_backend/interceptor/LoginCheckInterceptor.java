package org.example.HarmonyOS_backend.interceptor;


import com.alibaba.fastjson2.JSONObject;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.HarmonyOS_backend.Result.Result;
import org.example.HarmonyOS_backend.constan.JwtProperties;
import org.example.HarmonyOS_backend.tool.UserHolder;
import org.example.HarmonyOS_backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取请求头当中的(token)
        String jwt = request.getHeader("token");
        //如果jwt为空
        if (!StringUtils.hasLength(jwt)) {
            System.out.println("token为空");
            Result error = Result.error("NOT_LOGIN"); //给前端相应状态码
            //手动转为jason
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }
        //解析JWT
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(),jwt);
            //从jwt里获取用户id
            UserHolder.setUserId(claims.get("userId", Integer.class));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("解析令牌失败");
            Result error = Result.error("NOT_LOGIN");
            //手动转为jason
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }
        //放行
        System.out.println("放行");
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        UserHolder.remove();
    }
}
