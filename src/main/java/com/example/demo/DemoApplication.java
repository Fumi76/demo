package com.example.demo;

import java.util.function.Supplier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * hoge.instance1.type=a
 * hoge.instance2.type=b
 */
@SpringBootApplication
@RestController
public class DemoApplication {

	private static Log logger = LogFactory.getLog(DemoApplication.class);

//	@Autowired
//	Hoge hoge_instance1;
//	
//	@Autowired
//	Hoge hoge_instance2; // フィールド名(名前)でバインドできる

	@Autowired
//	@Qualifier("hoge_instance1") // あるいは@Qualifierで名前を指定
	Hoge hoge;

	@Autowired
//	@Qualifier("hoge_instance2")
	Hoge fuga;

	@Autowired
	Hoge bar;
	
	@Bean("bar")
	public Hoge bar() {
//	Hoge bar(GenericApplicationContext context) {
//		context.registerBean("cury", HogeImpl.class, new Supplier<HogeImpl>() {
//			@Override
//			public HogeImpl get() {
//				HogeImpl hoge = new HogeImpl();
//				hoge.setName("cury");
//				return hoge;
//			}}, new BeanDefinitionCustomizer() {
//				
//				@Override
//				public void customize(BeanDefinition bd) {
//				}
//			});
//		context.refresh();
		HogeImpl hoge = new HogeImpl();
		hoge.setName("bar");
		return hoge;
	}
	
	@RequestMapping("/test")
	public String test1() {
		logger.info("hoge "+hoge+" "+hoge.getName());
		logger.info("fuga "+fuga+" "+fuga.getName());
		logger.info("bar "+bar+" "+bar.getName());
		return "TEST1";
	}
	
//	@Override
//	public void run(String... arg0) throws Exception {
////		logger.info("->"+hoge_instance1.getName());
////		logger.info("->"+hoge_instance2.getName());
//		logger.info("->"+hoge.getName());
//		logger.info("->"+fuga.getName());
//	}
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
