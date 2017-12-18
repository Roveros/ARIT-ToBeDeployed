package com.browne.spring.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/*
 * @EnableWebSecurity Annotation is used to enable web security in
 * any web application. Since this class will be used to 
 * configure Spring Security it must extend WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {
	
	//Any role NOT including ADMIN
	String roles[] = {"ROLE_HOD_DPT1","ROLE_HOD_DPT2","ROLE_HOD_DPT3","ROLE_HOD_DPT4","ROLE_USER"};
	String anyRole = "hasAnyRole('"+roles[0]+"','"+roles[1]+"','"+roles[2]+"', '"+roles[3]+"', '"+roles[4]+"')";
	
	//Autowires spring security's UserDetailService
	@Autowired
	UserDetailsService userDetailsService;
	
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	/*
	 *antMatchers("foo").access("bar") prevents access to "foo" unless
	 *access level is "bar".
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
	  http.authorizeRequests()
		.antMatchers("/incidents").access(anyRole)
		.antMatchers("/incident").access(anyRole)
		.antMatchers("/notifications").access(anyRole)
		.antMatchers("/myprofile").access(anyRole)
		.antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/user").access("hasRole('ROLE_ADMIN')")	
		.antMatchers("/download/["+roles[1]+"]/*").access("hasRole('"+roles[1]+"')")
		.antMatchers("/download/["+roles[2]+"]/*").access("hasRole('"+roles[2]+"')")
		.antMatchers("/download/["+roles[3]+"]/*").access("hasRole('"+roles[3]+"')")
		.antMatchers("/download/["+roles[4]+"]/*").access("hasRole('"+roles[4]+"')")
		.and()
		  .formLogin().loginPage("/login").failureUrl("/login?error")
		  .usernameParameter("username").passwordParameter("password")
		.and()
		  .formLogin().defaultSuccessUrl("/default", true)
		.and() 
			.formLogin().loginProcessingUrl("/j_spring_security_check")
		.and()
			.logout().logoutUrl("/j_spring_security_logout")
		.and()
		  .logout().logoutSuccessUrl("/login?logout")
		.and()
		  .exceptionHandling().accessDeniedPage("/403")
		.and()
		  .csrf();		
	}
	
	//this bean will be used to bcrypt passwords for login authentification
	@Bean(name="passwordEncoder")
	public PasswordEncoder passwordEncoder() {		
		return new BCryptPasswordEncoder(4);		
	}
	
	//ref: http://www.mkyong.com/spring-security/spring-security-form-login-using-database/
}
