package com.system.springbootv1;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.filter.config.ConfigTools;
import com.system.springbootv1.common.autocode.CodeGenerator;
import com.system.springbootv1.common.autocode.FreeMarkerGeneratorUtil;
import com.system.springbootv1.common.redis.RedisUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootV1Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringbootV1ApplicationTests {

    @Resource
    CodeGenerator generator;

    @Test
    void contextLoads() {
    }

    /**
     * 密码加密
     *
     * @throws Exception
     */
    @Test
    public void decryptPassword() throws Exception {
        String password = "root";
        String[] arr = ConfigTools.genKeyPair(512);
        System.out.println("privateKey:" + arr[0]);
        System.out.println("publicKey:" + arr[1]);
        System.out.println("password:" + ConfigTools.decrypt(arr[0], password));
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

    @Test
    public void redisTest() {
        RedisUtil.set("test", 123, 20);

        System.out.println(RedisUtil.get("test"));
    }

    @Test
    public void freeMarkerTest1() {
        byte[] aaa = FreeMarkerGeneratorUtil.generator(
                generator.getDriverClassName(),
                generator.getUrl(),
                generator.getUsername(),
                generator.getPassword(),
                generator.getTableName(),
                generator.getDatabaseName(),
                generator.getTablePrefix());
        System.out.println(aaa);
    }

    @Test
    public void testPath() throws IOException {
//        Configuration conf = new Configuration();
//        File temp = new File("main/java/com/system/springbootv1/common/templates");
//        conf.setDirectoryForTemplateLoading(temp);
//        Template template = conf.getTemplate("Entity.ftl");
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        path = StrUtil.sub(path, 1, path.indexOf("/target"));


    }


}
