package com.epam.esm.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Class is deployment descriptor. It is a configuration file for an artifact
 * that is deployed to Tomcat.
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 * @see AbstractAnnotationConfigDispatcherServletInitializer
 */
public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private static final String DEV_PROFILE = "dev";
	private static final String PROD_PROFILE = "prod";
	private static final String ACTIVE_PROFILE_PARAM = "spring.profiles.active";

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.setInitParameter(ACTIVE_PROFILE_PARAM, PROD_PROFILE);
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebConfiguration.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
