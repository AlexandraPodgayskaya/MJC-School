package com.epam.esm.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Class contains service configuration
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */
@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class ServiceConfiguration {

	/**
	 * Create bean ModelMapper which will be used to parse entity to dto and
	 * opposite
	 *
	 * @return the model mapper
	 */
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setFieldMatchingEnabled(true)
				.setSkipNullEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
		return mapper;
	}

}
