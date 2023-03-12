package com.dorvak.webapp.moteur;

import com.dorvak.webapp.moteur.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MoteurWebApplication implements CommandLineRunner {

    private static MoteurWebApplication instance;
    public static final String VERSION = "1.0.0";
    public static final String APP_NAME = "Moteur Web";

    @Autowired
    private ConfigurableApplicationContext ctx;

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

}
