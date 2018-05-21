package com.grid.graph.main;

import com.grid.graph.generator.GridGraphGeneratorImpl;
import com.grid.graph.main.controller.GridGraphController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackageClasses=GridGraphController.class)
@ComponentScan(basePackageClasses=GridGraphGeneratorImpl.class)
@SpringBootApplication

public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
