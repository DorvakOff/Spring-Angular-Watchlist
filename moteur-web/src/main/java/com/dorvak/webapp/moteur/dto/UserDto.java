package com.dorvak.webapp.moteur.dto;

import com.dorvak.webapp.moteur.model.User;

import java.util.List;

public class UserDto extends User {

    public UserDto(User user, String... scopes) {
        this(user, List.of(scopes));
    }

    public UserDto(User user, List<String> scopes) {
        this.setUserId(user.getUserId());
        this.setUsername(user.getUsername());
        if (scopes.contains("email")) {
            this.setEmail(user.getEmail());
        }
    }
}
