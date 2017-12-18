package com.browne.spring.configuration;


import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.PropertySource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * This configuration class can be treated as a replacement for 
 * spring-servlet.xml as it contains all the information required for component-scanning and view resolver.
 *
 * @Configuration indicates the class contains one or more bean methods annotated
 * with @Bean producing bean manageable by spring container as an alternative to XML.
 * 
 * @EnableWebMvc is equivalent to mvc:annotation-driven in XML. 
 * It enables support for @Controller-annotated classes that 
 * use @RequestMapping to map incoming requests to specific method.
 * 
 * @ComponentScan is equivalent to context:component-scan base-package="..."
 * providing with where to look for spring managed beans/classes.
 * 
 * @author Roveros
 *
 */
@PropertySource(value = { "classpath:application.properties" })
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.browne.spring")
@Import(value = { LoginSecurityConfig.class })
public class AritConfiguration extends WebMvcConfigurerAdapter {
	
	//Used to access values stored in src/main/resources/application.properites
    @Autowired
    private Environment environment;
	
    //Used to supply the database connection configuration
	@Autowired
	DataSource dataSource;
	
	/*
	 * UserDetailSevice used to connect to the database to query username and passwords
	 * Sets users authority/role based on query results
	 */
	@Bean(name="userDetailsService")
	public UserDetailsService userDetailsService() {
		JdbcDaoImpl jdbcImpl = new JdbcDaoImpl();
		jdbcImpl.setDataSource(dataSource);
		jdbcImpl.setUsersByUsernameQuery("select username,password, enabled from users where username=?");
		jdbcImpl.setAuthoritiesByUsernameQuery("select username, role from user_roles where username=?");
		return jdbcImpl;
	}
	
	//configuration for uploading files
    @Bean(name="multipartResolver") 
    public CommonsMultipartResolver getResolver() throws IOException{
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
         
        //Set the maximum allowed size (in bytes) for each individual file.
        resolver.setMaxUploadSize(Integer.valueOf(environment.getRequiredProperty("multipartresolver.maxuploadsize")));	// -1 = No limit
        
        return resolver;
    }
    
    //Configuration for a java mail sender
    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
         
        mailSender.setHost(environment.getRequiredProperty("javamailsender.host"));
        mailSender.setPort(Integer.valueOf(environment.getRequiredProperty("javamailsender.port")));
        mailSender.setUsername(environment.getRequiredProperty("javamailsender.username"));
        mailSender.setPassword(environment.getRequiredProperty("javamailsender.password"));
         
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", environment.getRequiredProperty("mail.smtp.starttls.enable"));
        javaMailProperties.put("mail.smtp.auth", environment.getRequiredProperty("mail.smtp.auth"));
        javaMailProperties.put("mail.transport.protocol", environment.getRequiredProperty("mail.transport.protocol"));
        javaMailProperties.put("mail.debug", environment.getRequiredProperty("mail.debug"));
         
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

	/**
	 * This bean acts as an alternative to an XML defined view resolver. 
	 * String values for page requests will have the prefix and suffix added
	 * @return viewResolver
	 */
	@Bean
	public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");        
        return viewResolver;		
	}
	
	//Will allow for access to messages.properties for validation messages
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }
	
    /*
     * Configure ResourceHandlers to serve static resources like CSS, JS, images etc...
     */	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    } 
	
}
