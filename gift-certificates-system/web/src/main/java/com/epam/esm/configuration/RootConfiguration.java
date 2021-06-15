package com.epam.esm.configuration;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Class contains spring configuration
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
public class RootConfiguration {
	@Value("${db.driver}")
	private String driverClassName;
	@Value("${db.url}")
	private String url;
	@Value("${db.user}")
	private String userName;
	@Value("${db.password}")
	private String password;
	@Value("${db.poolsize}")
	private int poolSize;
	@Value("classpath:script/create_tables_script.sql")
	private String createScript;
	@Value("classpath:script/init_tables_script.sql")
	private String initScript;

	/**
	 * Create bean DataSource which will be used as data source in prod profile
	 *
	 * @return the data source
	 */
	@Profile("prod")
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		dataSource.setInitialSize(poolSize);
		return dataSource;
	}

	/**
	 * Create bean DataSource which will be used as data source in dev profile
	 *
	 * @return the data source
	 */
	@Profile("dev")
	@Bean
	public DataSource embeddedDataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript(createScript)
				.addScript(initScript).build();
	}

	/**
	 * Create bean JdbcTemplate which will be used for queries to database
	 *
	 * @param dataSource the data source
	 * @return the jdbc template
	 */
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	/**
	 * Create bean TransactionManager which will be used to manage transactions
	 *
	 * @param dataSource the data source
	 * @return the transaction manager
	 */
	@Bean
	public TransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

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
