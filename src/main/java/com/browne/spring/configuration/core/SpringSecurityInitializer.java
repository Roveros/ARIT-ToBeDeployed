package com.browne.spring.configuration.core;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/*
 * “SpringSecurityInitializer” is used to register the DelegatingFilterProxy
 *  to use the springSecurityFilterChain. It avoids writing Filters configuration in 
 *  web.xml file when using annotation based configured applications.
 */

public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

}
