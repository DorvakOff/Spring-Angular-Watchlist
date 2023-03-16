package com.dorvak.webapp.moteur;

import com.dorvak.webapp.moteur.security.bearer.CustomBearerAuthenticationManager;
import com.dorvak.webapp.moteur.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Configuration
@EnableJpaRepositories("com.dorvak.webapp")
@ComponentScan("com.dorvak.webapp")
@EntityScan("com.dorvak.webapp")
public class MoteurWebApplication implements CommandLineRunner {

    public static final String VERSION = "1.0.0";
    public static final String APP_NAME = "Moteur Web";
    private static MoteurWebApplication instance;
    @Autowired
    private ConfigurableApplicationContext ctx;
    @Autowired
    private CustomBearerAuthenticationManager customBearerAuthenticationManager;

    public static MoteurWebApplication getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        SpringApplication.run(MoteurWebApplication.class, args);
    }

    @Override
    public void run(String... args) {
        LoggerUtils.info("Starting MoteurWebApplication...");
        instance = this;

        Thread shutdownHook = new Thread(this::shutdown, "ShutdownHook");
        Runtime.getRuntime().addShutdownHook(shutdownHook);
        LoggerUtils.info("MoteurWebApplication started.");
    }

    private void shutdown() {
        LoggerUtils.info("Stopping MoteurWebApplication...");
        if (ctx.isRunning()) {
            ctx.stop();
        }
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedHeaders("*")
                        .allowedMethods("*");
            }
        };
    }

    public CustomBearerAuthenticationManager getCustomBearerAuthenticationManager() {
        return customBearerAuthenticationManager;
    }

    public ConfigurableApplicationContext getApplicationContext() {
        return ctx;
    }
}
