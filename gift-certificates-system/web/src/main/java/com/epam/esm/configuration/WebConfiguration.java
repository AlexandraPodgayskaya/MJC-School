package com.epam.esm.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.epam.esm.converter.StringToOrderTypeConverter;
import com.epam.esm.converter.StringToSortTypeConverter;

@Configuration
@EnableWebMvc
@ComponentScan("com.epam.esm")
public class WebConfiguration implements WebMvcConfigurer {
	@Value("UTF-8")
	private String encoding;
	@Value("exception")
	private String fileName;

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToSortTypeConverter());
		registry.addConverter(new StringToOrderTypeConverter());
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename(fileName);
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding(encoding);
		return messageSource;
	}
}
