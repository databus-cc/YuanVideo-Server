package cc.databus.video;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * resource mapping
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // TODO: the path should not be hard coded here
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/") // 用来支持swagger
                .addResourceLocations("file:/Users/jianyuan/Personal/Codes/yuanvideo/api/space/");// 最后的'/'必须有
    }
}
