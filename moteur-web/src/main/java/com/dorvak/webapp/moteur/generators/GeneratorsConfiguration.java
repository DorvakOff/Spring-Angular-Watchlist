package com.dorvak.webapp.moteur.generators;

import com.dorvak.webapp.moteur.MoteurWebApplication;
import ink.organics.idgenerator.IDGeneratorManager;
import ink.organics.idgenerator.decorator.Decorator;
import ink.organics.idgenerator.generator.impl.SnowflakeGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GeneratorsConfiguration {

    @Bean
    public IDGeneratorManager idGeneratorManager() {
        return IDGeneratorManager.getInstance()
                .init(Decorator.builder()
                        .generatorId("snowflake_generator")
                        .generator(SnowflakeGenerator.build(MoteurWebApplication.APP_NAME, List.of(MoteurWebApplication.APP_NAME)))
                        .build());
    }
}
