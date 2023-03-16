package com.dorvak.webapp.moteur.security.bearer;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.dorvak.webapp.moteur.model.User;
import com.dorvak.webapp.moteur.repository.UserRepository;
import com.dorvak.webapp.moteur.security.keygen.KeyManager;
import com.dorvak.webapp.moteur.utils.CharacterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomBearerAuthenticationManager {

    @Autowired
    private KeyManager keyManager;

    @Autowired
    private UserRepository userRepository;

    public CustomBearerAuthentication authenticate(CustomBearerAuthentication authentication) {

        String token = Objects.toString(authentication.getCredentials(), "");

        if (CharacterUtils.isNotEmptyTrim(token)) {
            try {
                DecodedJWT decodedJWT = keyManager.getJwtGenerator().verifyToken(token);
                authentication.setAuthenticated(true);
                String userId = decodedJWT.getClaim("userId").asString();
                User user = userRepository.findById(userId).orElse(null);
                authentication.setDetails(user);
            } catch (Exception e) {
                authentication.setAuthenticated(false);
            }
        }
        return authentication;
    }
}
