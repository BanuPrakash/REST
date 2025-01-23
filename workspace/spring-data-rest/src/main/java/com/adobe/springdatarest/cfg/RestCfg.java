package com.adobe.springdatarest.cfg;

import com.adobe.springdatarest.entity.Product;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestCfg implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
//        cors.addMapping("/api/products").allowedOrigins("http://adobe.com");

        ExposureConfiguration exposureConfiguration = config.getExposureConfiguration();
        exposureConfiguration.forDomainType(Product.class)
                .withItemExposure( (metdata, httpMethods) -> {
                   return httpMethods.disable(HttpMethod.DELETE);
                });
    }
}
