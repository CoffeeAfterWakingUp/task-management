package kz.bitlab.taskmanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/sidebar").setViewName("sidebar");
        registry.addViewController("/workspace").setViewName("workspace");
        registry.addViewController("/boards").setViewName("workspace");
        registry.addViewController("/members").setViewName("members");
        registry.addViewController("/settings").setViewName("settings");
        registry.addViewController("/board").setViewName("board");
    }
}
