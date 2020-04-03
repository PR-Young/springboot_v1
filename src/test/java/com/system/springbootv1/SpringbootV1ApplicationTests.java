package com.system.springbootv1;

import com.system.springbootv1.common.autocode.CodeGenerator;
import com.system.springbootv1.common.autocode.FreeMarkerGeneratorUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootV1Application.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringbootV1ApplicationTests {

	@Resource
	CodeGenerator generator;

	@Test
	void contextLoads() {
	}

	@Test
	public void freeMarkerTest() {
		FreeMarkerGeneratorUtil.generatorMvcCode(
				generator.getDriverClassName(),
				generator.getUrl(),
				generator.getUsername(),
				generator.getPassword(),
				generator.getTableName(),
				generator.getDatabaseName(),
				generator.getTablePrefix(),
				generator.getGenenaterLevel(),
				generator.getBasePackage(),
				generator.getDaoPackage(),
				generator.getXmlDir(),
				generator.getServicePackage(),
				generator.getControllerPackage());
	}
}
