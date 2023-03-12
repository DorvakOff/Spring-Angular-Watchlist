package com.dorvak.webapp.moteur.security.bearer;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class CustomBearerAuthentication extends AbstractAuthenticationToken {
    private final String authToken;

    public CustomBearerAuthentication(String authToken) {
        super(null);
        setAuthenticated(false);
        this.authToken = authToken;
    }

    @Override
    public Object getCredentials() {
        return authToken;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
