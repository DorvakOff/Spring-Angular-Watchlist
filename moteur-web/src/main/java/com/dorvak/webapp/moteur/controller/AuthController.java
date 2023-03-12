package com.dorvak.webapp.moteur.controller;

import com.dorvak.webapp.moteur.security.keygen.KeyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private KeyManager keyManager;

    @PostMapping("/login")
    @ResponseBody
    public String login() {
        return keyManager.getJwtGenerator().generateToken(157943514200539136L);
    }
}
