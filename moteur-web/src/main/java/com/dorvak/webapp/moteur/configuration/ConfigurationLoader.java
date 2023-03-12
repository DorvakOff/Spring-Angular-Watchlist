package com.dorvak.webapp.moteur.configuration;

import com.dorvak.webapp.moteur.utils.FileUtils;
import com.dorvak.webapp.moteur.utils.LoggerUtils;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@org.springframework.context.annotation.Configuration
public class ConfigurationLoader {

    private Configuration configuration;
    private final File configFile = new File("WEB-INF/config.json");

    @Bean
    public Configuration load() {
        FileUtils.createDirectory("WEB-INF");
        if (configFile.exists()) {
            JsonMapper mapper = new JsonMapper();
            try (FileReader reader = new FileReader(configFile)) {
                configuration = mapper.readValue(reader, Configuration.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            configuration = new Configuration();
        }
        updateConfiguration();
        LoggerUtils.info("Loaded config file");
        return configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void updateConfiguration() {
        try (FileWriter writer = new FileWriter(configFile)) {
            if (!configFile.exists() && configFile.createNewFile()) {
                LoggerUtils.info("Created new config file");
            }
            JsonMapper mapper = new JsonMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(writer, configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
