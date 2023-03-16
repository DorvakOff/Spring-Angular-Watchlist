package com.dorvak.webapp.moteur.security.bearer;

import com.dorvak.webapp.moteur.model.User;

public class CustomBearerAuthentication {
    private final String authToken;
    private boolean authenticated = false;
    private User user;

    public CustomBearerAuthentication(String authToken) {
        setAuthenticated(false);
        this.authToken = authToken;
    }

    public Object getCredentials() {
        return authToken;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public User getDetails() {
        return user;
    }

    public void setDetails(User user) {
        this.user = user;
    }
}
