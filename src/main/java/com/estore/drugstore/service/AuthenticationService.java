package com.estore.drugstore.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estore.drugstore.config.MessageStrings;
import com.estore.drugstore.exceptions.AuthenticationFailException;
import com.estore.drugstore.model.AuthenticationToken;
import com.estore.drugstore.model.User;
import com.estore.drugstore.repository.TokenRepository;

@Service
public class AuthenticationService {

    @Autowired
    TokenRepository tokenRepository;

    // save the confirmation token
    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    // get token of the User
    public AuthenticationToken getToken(User user) {
        return tokenRepository.findTokenByUser(user);
    }

    // get User from the token
    public User getUser(String token) {
        AuthenticationToken authenticationToken = tokenRepository.findTokenByToken(token);
        if (Objects.nonNull(authenticationToken)) {
            if (Objects.nonNull(authenticationToken.getUser())) {
                return authenticationToken.getUser();
            }
        }
        return null;
    }

    // check if the token is valid
    public void authenticate(String token) throws AuthenticationFailException {
        if (!Objects.nonNull(token)) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_PRESENT);
        }
        if (!Objects.nonNull(getUser(token))) {
            throw new AuthenticationFailException(MessageStrings.AUTH_TOKEN_NOT_VALID);
        }
    }
}