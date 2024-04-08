//package com.example.myblog.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
//
//
//@Configuration // 하나이상의 빈을 정의하고 관리하는 클래스임을 나타냄.
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    @Value("${uploadPath}") // = String uploadPath = "file:///c:/blog/post/"
//    String uploadPath;
//    @Bean
//    MappingJackson2JsonView jsonVIew() { // 데이터를 json객체로 리턴해준다.
//        return new MappingJackson2JsonView();
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//        //localhost/images로 시작하는 모든 경로의 파일들은 uploadPath에서 찾아오겠다.
//        //uploadPath에 이미지가 있어야 한다. (그래서 static/images에 있던거 다 옮겨줬다.)
//        registry.addResourceHandler("/images/**") // **은 localhost/images폴더의 모든 하위경로
//                .addResourceLocations(uploadPath);
//    }
//
//}


package com.example.myblog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${uploadPath}") //String uploadPath = "file:///C:/blog/post/
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //images 폴더의 모든 하위 경로
        /*
            localhost/images
        *  localhost/images/test
        * localhost/images/test/hello
        * */

        //localhost/images로 시작하는 모든 경로의 파일들은 uploadPath에서 찾아오겠다
        //uploadPath에 이미지가 있어야한다.
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }

    @Bean
    MappingJackson2JsonView jsonView(){ //데이터를 json 객체로 리턴해준다.
        return new MappingJackson2JsonView();
    }
}