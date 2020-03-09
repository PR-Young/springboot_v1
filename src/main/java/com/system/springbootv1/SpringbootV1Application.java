package com.system.springbootv1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.system.springbootv1.dao")
@SpringBootApplication
public class SpringbootV1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootV1Application.class, args);
        System.out.println("         ______                                     __    __ ");
        System.out.println("		/      \\                                   /  |  /  |");
        System.out.println("       /$$$$$$  |  ______   _____  ____    ______  $$ | _$$ |_     ______");
        System.out.println("       $$ |  $$/  /      \\ /     \\/   \\  /     \\   $$ |/ $$   |   /     \\");
        System.out.println("       $$ |      /$$$$$$  |$$$$$$ $$$$  |/$$$$$$  |$$ |$$$$$$/   /$$$$$$  |");
        System.out.println("	   $$ |   __ $$ |  $$ |$$ | $$ | $$ |$$ |  $$ |$$ |  $$ | __ $$    $$ |");
        System.out.println("       $$ \\__/  |$$ \\__$$ |$$ | $$ | $$ |$$ |__$$ |$$ |  $$ |/  |$$$$$$$$/");
        System.out.println("	   $$    $$/ $$    $$/ $$ | $$ | $$ |$$    $$/ $$ |  $$  $$/ $$       |");
        System.out.println("	   $$$$$$/    $$$$$$/  $$/  $$/  $$/ $$$$$$$/  $$/    $$$$/   $$$$$$$/");
        System.out.println("				                         $$ |");
        System.out.println("		                                 $$ |");
        System.out.println("		                                 $$/");
    }

}
