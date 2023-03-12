package com.dorvak.webapp.moteur.security.bearer;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.dorvak.webapp.moteur.model.User;
import com.dorvak.webapp.moteur.repository.UserRepository;
import com.dorvak.webapp.moteur.security.keygen.KeyManager;
import com.dorvak.webapp.moteur.utils.CharacterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomBearerAuthenticationManager implements AuthenticationManager {

    @Autowired
    private KeyManager keyManager;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomBearerAuthentication bearerAuthentication = (CustomBearerAuthentication) authentication;

        String token = Objects.toString(bearerAuthentication.getCredentials(), "");

        if (CharacterUtils.isNotEmptyTrim(token)) {
            try {
                DecodedJWT decodedJWT = keyManager.getJwtGenerator().verifyToken(token);
                bearerAuthentication.setAuthenticated(true);
                String userId = decodedJWT.getClaim("userId").asString();
                User user = userRepository.findById(userId).orElse(null);
                bearerAuthentication.setDetails(user);
            } catch (Exception e) {
                bearerAuthentication.setAuthenticated(false);
            }
        }
        return bearerAuthentication;
    }
}
