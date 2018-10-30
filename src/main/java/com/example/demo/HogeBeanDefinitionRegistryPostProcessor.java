package com.example.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.ConfigurableEnvironment;

//@Configuration
public class HogeBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {
	
	private static Log logger = LogFactory.getLog(HogeBeanDefinitionRegistryPostProcessor.class);

//	@Autowired
	ConfigurableEnvironment environment;
	
	public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0) throws BeansException {
		// TODO Auto-generated method stub
	}

	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanRegistry) throws BeansException {
		logger.info("CALL!!!");
//		logger.info("@@@"+environment.getClass());
		//createDynamicBeans(environment,beanRegistry);
		
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(HogeImpl.class);
		builder.addPropertyValue("name", "HOGE2");
		
		beanRegistry.registerBeanDefinition("hoge", builder.getBeanDefinition());
		
		
		builder = BeanDefinitionBuilder.genericBeanDefinition(HogeImpl.class);
		builder.addPropertyValue("name", "FUGA2");
		
		beanRegistry.registerBeanDefinition("fuga", builder.getBeanDefinition());

//		GenericBeanDefinition gbd = new GenericBeanDefinition();
//		gbd.setBeanClass(HogeImpl.class);
//		beanRegistry.registerBeanDefinition("bar", gbd);

//		RootBeanDefinition rbd = new RootBeanDefinition(HogeImpl.class);
//		MutablePropertyValues mpv = new MutablePropertyValues();
//		mpv.addPropertyValue("name", "HOGE!");
//		rbd.setPropertyValues(mpv);
//		beanRegistry.registerBeanDefinition("hoge", rbd);
		
//		RootBeanDefinition fuga = new RootBeanDefinition(HogeImpl.class);
//		MutablePropertyValues mpv2 = new MutablePropertyValues();
//		mpv2.addPropertyValue("name", "FUGE!");
//		fuga.setPropertyValues(mpv2);
//		beanRegistry.registerBeanDefinition("fuga", fuga);
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return Ordered.HIGHEST_PRECEDENCE;
	}

}
