package com.ez2pay.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        //use path extension and query parameter and accept header methods altogether
        configurer.favorParameter(true)
                  .parameterName("mediaType")
                  .ignoreAcceptHeader(false)
                  .useRegisteredExtensionsOnly(false)
                  .defaultContentType(MediaType.APPLICATION_JSON)
                  .mediaType("xml", MediaType.APPLICATION_XML)
                  .mediaType("json", MediaType.APPLICATION_JSON);


        /*//use only path extension method
        configurer.favorParameter(false)
                  .ignoreAcceptHeader(true)
                  .defaultContentType(MediaType.APPLICATION_JSON)
                  .mediaType("xml", MediaType.APPLICATION_XML)
                  .mediaType("json", MediaType.APPLICATION_JSON);*/


        /*//use only query parameter method
        configurer.favorPathExtension(false);
        configurer.favorParameter(true)
                  .parameterName("mediaType")
                  .ignoreAcceptHeader(true)
                  .useRegisteredExtensionsOnly(false)
                  .defaultContentType(MediaType.APPLICATION_JSON)
                  .mediaType("xml", MediaType.APPLICATION_XML)
                  .mediaType("json", MediaType.APPLICATION_JSON);*/


       /* //use only accept header method
        configurer.favorPathExtension(false);
        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("json", MediaType.APPLICATION_JSON);*/

    }
}
