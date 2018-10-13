package com.example.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * hoge.instance1.type=a
 * hoge.instance2.type=b
 */
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	private static Log logger = LogFactory.getLog(DemoApplication.class);

//	@Autowired
//	Hoge hoge_instance1;
//	
//	@Autowired
//	Hoge hoge_instance2; // フィールド名(名前)でバインドできる

	@Autowired
	@Qualifier("hoge_instance1") // あるいは@Qualifierで名前を指定
	Hoge hoge;

	@Autowired
	@Qualifier("hoge_instance2")
	Hoge fuga;

	@Override
	public void run(String... arg0) throws Exception {
//		logger.info("->"+hoge_instance1.getName());
//		logger.info("->"+hoge_instance2.getName());
		logger.info("->"+hoge.getName());
		logger.info("->"+fuga.getName());
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
