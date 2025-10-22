package org.example.HarmonyOS_backend.config;

import org.example.HarmonyOS_backend.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //配置类
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有资源          //不需要拦截的资源
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/api/image/imageUpload","/api/userLike/changeUserLike",
                "/api/comments/postComments","/api/comments/deleteComments","/api/image/getMyImage",
                "/api/image/getMyImageNumber","/api/image/getImageRandomly","/api/user/changeUserHeadshot",
                "/api/image/deleteImage","/api/bookmark/changeBookmark","/api/bookmark/getBookmarkList",
                "/api/image/getImageInformation","/api/bookmark/getBrowsingHistoryList","/api/image/searchImage",
                "/api/user/getUserBasicInformation","/api/user/changePassword","/api/image/imageUpload1").excludePathPatterns("/api/user/login","/api/user/register",
                "/api/email/sendVerificationCode","/api/email/verifyCode");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 本地图片存储路径（例如：D:/upload/images/）
//        String imagePath = "file:D:/upload/images/";
        String imagePath = "file:/opt/HarmonyOS/upload/images/";
        // 映射 URL 路径：访问 http://域名/images/xxx.png 时，实际读取本地路径
        registry.addResourceHandler("/images/**")
                .addResourceLocations(imagePath);
    }
}
