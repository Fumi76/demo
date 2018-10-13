package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Repository;

@Configuration
public class MyConfiguration {

	private static Log logger = LogFactory.getLog(MyConfiguration.class);
	
	/**
	 * Create a beanPostProcessor , @Bean for adding the dynamic beans.
	 */
	@Bean
	static BeanDefinitionRegistryPostProcessor beanPostProcessor(final ConfigurableEnvironment environment) {
		
		return new BeanDefinitionRegistryPostProcessor() {

			public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0) throws BeansException {
				// TODO Auto-generated method stub
			}

			public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanRegistry) throws BeansException {
				logger.info("@@@"+environment.getClass());
				createDynamicBeans(environment,beanRegistry);
			}
			
		};
	}
	
	static private void createDynamicBeans(ConfigurableEnvironment environment,BeanDefinitionRegistry beanRegistry) {
		Map<String, HogeProperties> propertyMap = parseProperties(environment);
		for(Map.Entry<String, HogeProperties> entry : propertyMap.entrySet()) {
			registerDynamicBean(entry.getKey(),entry.getValue(),beanRegistry);
		}
	}
	
	/**
	 * @param environment The environment which properties can be extracted from.
	 * @return A map of DataSourceProperties for each prefix.
	 */
	static private Map<String, HogeProperties> parseProperties(ConfigurableEnvironment environment) {
		Map<String, HogeProperties> propertyMap = new HashMap<>();
		
		for(PropertySource source : environment.getPropertySources()) {
			if(source instanceof EnumerablePropertySource) {
				EnumerablePropertySource propertySource = (EnumerablePropertySource) source;
				for(String property : propertySource.getPropertyNames()) {
					String value = environment.getProperty(property, String.class);
					logger.info("PROPERTY "+property+"="+value);
//					if(value == null) {
//						throw new IllegalStateException(prefix + "." + propertyBase + "." + property +" is not found" );
//					}
					
					if(property.startsWith("hoge.") && property.endsWith("type")) {
						int index = property.indexOf(".", "hoge.".length());
						if(index > -1) {
							String sub = property.substring("hoge.".length(), index);
							if(sub.trim().length() == 0) {
								continue;
							}
							HogeProperties prop = propertyMap.get("hoge."+sub);
							if(prop == null) {
								prop = new HogeProperties();
							}
							prop.setName(sub);
							prop.setType(value);
							propertyMap.put("hoge."+sub, prop);
						}
					}
					
//					if(DataSourceProperties.isUrlProperty(property)) {
//						String prefix = extractPrefix(property);
//						propertyMap.put(prefix, new DataSourceProperties(environment,prefix));
//					}
				}
			}
		}
		return propertyMap;
	}

	/**
	 * This function will create the dynamic bean definitions.
	 * @param prefix  The prefix for the beans we are creating.
	 * @param dsProps  The properties for the datasource
	 * @param beanRegistry  The bean registry we add the beans to
	 */
	static private void registerDynamicBean(String prefix, HogeProperties props, BeanDefinitionRegistry beanRegistry) {
		
		logger.info("Registering beans for " + prefix);
		
		BeanDefinition hogeBeanDef = BeanDefinitionBuilder.genericBeanDefinition(Hoge.class)
				.addPropertyValue("name", props.getType())
//				.addPropertyValue("username", dsProps.getUsername())
//				.addPropertyValue("password", dsProps.getPassword())
//				.addPropertyValue("driverClassName", dsProps.getDriver())
				.getBeanDefinition();
		
//		if(dsProps.getPrimary()) {
//			dataSourceBeanDef.setPrimary(true);
//		}

		beanRegistry.registerBeanDefinition("hoge_"+props.getName(), hogeBeanDef);

		//		if(dsProps.getPrimary()) {
//			beanRegistry.registerAlias("datasource_" + prefix, "dataSource");
//		}
		
//		BeanDefinition repositoryBeanDef = BeanDefinitionBuilder.genericBeanDefinition(Repository.class)
//				.addConstructorArgReference("datasource_" + prefix)
//				.getBeanDefinition();
		
//		beanRegistry.registerBeanDefinition("repository_" + prefix, repositoryBeanDef);
		
	}
}
