package com.blog.blogappapis.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    public static final String AUTHORIZATION_HEADER="Authorization";
    private ApiKey apiKeys(){
        return new ApiKey("JWT",AUTHORIZATION_HEADER,"header");
    }
    private List<SecurityContext> securityContext(){
        return Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());
    }
    private List<SecurityReference> sf(){
        AuthorizationScope as=new AuthorizationScope("global","accessEverything");
        return Arrays.asList(new SecurityReference("JWT",new AuthorizationScope[]{as}));
    }

    @Bean
    public Docket api(){

     return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo())
             .securityContexts(securityContext())
             .securitySchemes(Arrays.asList(apiKeys()))
             .select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }
    private ApiInfo getInfo(){
        return new ApiInfo("Blogging Application: Backend course","This project is developed by Subhajit","1.0","Terms of Service",
                new Contact("Subhajit","http://abc.com","subhojit174@gmail.com"),"License of Apis","Api License URL",
                Collections.emptyList());
    }
}
