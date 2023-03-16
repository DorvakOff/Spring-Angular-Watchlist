package com.dorvak.webapp.moteur.security.keygen;

import com.dorvak.webapp.moteur.repository.UserRepository;
import com.dorvak.webapp.moteur.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KeyManager {

    private final Map<String, KeyGenerator> keyGenerators;
    private final JwtGenerator jwtGenerator;
    @Autowired
    private UserRepository userRepository;

    public KeyManager() {
        keyGenerators = new HashMap<>();
        jwtGenerator = new JwtGenerator(this);
    }

    public KeyGenerator addKeyGenerator(String name) {
        try {
            keyGenerators.put(name, new KeyGenerator(name));
        } catch (Exception e) {
            e.printStackTrace();
            LoggerUtils.severe("Failed to create key generator for %s", name);
        }
        return keyGenerators.get(name);
    }

    public KeyGenerator addKeyGenerator(Class<?> clazz) {
        return this.addKeyGenerator(clazz.getSimpleName());
    }

    public JwtGenerator getJwtGenerator() {
        return jwtGenerator;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}