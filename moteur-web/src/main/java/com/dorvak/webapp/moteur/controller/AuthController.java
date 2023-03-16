package com.dorvak.webapp.moteur.controller;

import com.dorvak.webapp.moteur.dto.LoginDto;
import com.dorvak.webapp.moteur.dto.UserDto;
import com.dorvak.webapp.moteur.model.User;
import com.dorvak.webapp.moteur.security.PasswordManager;
import com.dorvak.webapp.moteur.security.keygen.KeyManager;
import com.dorvak.webapp.moteur.utils.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private KeyManager keyManager;

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody() LoginDto dto, HttpServletRequest request) {
        User user = keyManager.getUserRepository().findByUsernameOrEmail(dto.username(), dto.email());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if (!PasswordManager.checkPassword(dto.password(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        String ip = IpUtils.getIp(request);
        user.setLastLogin(Instant.now());
        user.setLastLoginIp(ip);
        keyManager.getUserRepository().save(user);
        return keyManager.getJwtGenerator().generateToken(user.getUserId());
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestBody() LoginDto dto, HttpServletRequest request) {
        if (keyManager.getUserRepository().existsByUsernameOrEmail(dto.username(), dto.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User or email already exists");
        }
        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(PasswordManager.encryptPassword(dto.password()));
        user.setLastLogin(Instant.now());
        user.setLastLoginIp(IpUtils.getIp(request));
        keyManager.getUserRepository().save(user);
        return keyManager.getJwtGenerator().generateToken(user.getUserId());
    }

    @GetMapping("/user")
    @ResponseBody
    public UserDto getUser(@RequestHeader("Authorization") String token) {
        User user = keyManager.getJwtGenerator().getUserFromToken(token);
        return new UserDto(user, "email");
    }
}
