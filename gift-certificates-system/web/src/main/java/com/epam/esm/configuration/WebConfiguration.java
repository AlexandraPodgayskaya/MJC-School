package com.epam.esm.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.epam.esm.converter.StringToOrderTypeConverter;
import com.epam.esm.converter.StringToSortTypeConverter;

/**
 * Class contains spring web configuration
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 * @see WebMvcConfigurer
 */
@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class WebConfiguration implements WebMvcConfigurer {
	@Value("UTF-8")
	private String encoding;
	@Value("localization/exception")
	private String fileName;

	/**
	 * Add converters in addition to the ones registered by default
	 *
	 * @param registry the formatter registry
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToSortTypeConverter());
		registry.addConverter(new StringToOrderTypeConverter());
	}

	/**
	 * Create bean MessageSource which will be used to get info from properties
	 * files
	 *
	 * @return the message source
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename(fileName);
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding(encoding);
		return messageSource;
	}
}
