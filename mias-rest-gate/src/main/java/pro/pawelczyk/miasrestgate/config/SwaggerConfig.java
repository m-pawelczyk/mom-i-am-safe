package pro.pawelczyk.miasrestgate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 17.06.2020
 * created SwaggerConfig in pro.pawelczyk.miasrestgate.config
 * in project mias-rest-gate
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("pro.pawelczyk.miasrestgate.controllers"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");
    }
}
