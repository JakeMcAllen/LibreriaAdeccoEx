package conf;

import java.util.List;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

@Configuration
@ComponentScan(basePackages = "conf, controller, dao, dto, entity, restController, services")
@EnableTransactionManagement
@EnableWebMvc
public class libreriaConfig implements WebMvcConfigurer {
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//configurazione standard per tutti i blocchi json
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.indentOutput(true).serializationInclusion(Include.NON_NULL).propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		
		//registrazione del converter per json
		converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
		
		//registrazione del converter per xml
		converters.add(new MappingJackson2XmlHttpMessageConverter(builder.createXmlMapper(true).build()));
		
	}
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver res = new InternalResourceViewResolver();
		res.setPrefix("/");
		res.setSuffix(".jsp");
		return res;
	}
	
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds) {
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(ds);
		entityManagerFactoryBean.setPackagesToScan(new String[] { "entity" });
		
		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
		entityManagerFactoryBean.setJpaProperties(additionalProperties());
		
		return entityManagerFactoryBean;
	}



	final Properties additionalProperties() {
		final Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		hibernateProperties.setProperty("hibernate.format_sql", "true");
		
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		
		return hibernateProperties;
	}




	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource d = new DriverManagerDataSource();
		d.setUsername("root");
		d.setPassword("root");
		d.setUrl("jdbc:mysql://localhost:3306/libreriadb?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false");
		d.setDriverClassName("com.mysql.cj.jdbc.Driver");
		return d;
	}



	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager tx = new JpaTransactionManager();
		tx.setEntityManagerFactory(emf);
		return tx;
	}
	
}
