package com.grid.graph.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.grid.graph.generator.ConsensusGridGraphGeneratorImpl;

@Configuration
public class ConsensusGridGraphImplConfig {
        @Bean(name="GridGraphImpl")
        public ConsensusGridGraphGeneratorImpl GridGraphImpl(){
            return new ConsensusGridGraphGeneratorImpl();
        }
}
