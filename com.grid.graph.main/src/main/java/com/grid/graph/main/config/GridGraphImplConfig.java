package com.grid.graph.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.grid.graph.generator.GridGraphGeneratorImpl;

@Configuration
public class GridGraphImplConfig {
        @Bean(name="GridGraphImpl")
        public GridGraphGeneratorImpl GridGraphImpl(){
            return new GridGraphGeneratorImpl();
        }
}
