package com.browne.spring.configuration.core;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.browne.spring.configuration.AritConfiguration;

/*
 * This class acts as a replacement of any spring configuration that would be defined in web.xml
 * Things like assigning url mapping "/", configuring the DispatcherServet and registering the
 * root configuration class.
 * 
 */
public class AritInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
 
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { AritConfiguration.class };
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
 /*
  * ref: http://websystique.com/springmvc/spring-4-mvc-helloworld-tutorial-annotation-javaconfig-full-example/
  */
}
