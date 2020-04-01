package com.system.springbootv1;

import com.system.springbootv1.common.autocode.CodeGenerator;
import com.system.springbootv1.common.autocode.FreeMarkerGeneratorUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootV1ApplicationTests {



	@Test
	void contextLoads() {
	}

	@Test
	public void freeMarkerTest() {
		CodeGenerator generator = new CodeGenerator();

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
