package com.springboilerplate.springboilerplate.security;

import com.springboilerplate.springboilerplate.app.user.User;
import com.springboilerplate.springboilerplate.app.user.UserRepository;
import com.springboilerplate.springboilerplate.exceptions.InvalidTokenException;
import com.springboilerplate.springboilerplate.exceptions.SendingTokenException;
import com.springboilerplate.springboilerplate.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Optional;

@Service
public class TokenAuthenticationService {

    private static final String AUTH_HEADER_NAME = "AUTHORIZATION";
    private static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;
    private final TokenHandler tokenHandler;
    private UserRepository userRepository;

    @Autowired
    public TokenAuthenticationService(Environment env, UserRepository userRepository) {
        String secret = env.getProperty("MOBSTAFF_TOKEN_SECRET");
        tokenHandler = new TokenHandler(DatatypeConverter.parseBase64Binary(secret));
        this.userRepository = userRepository;
    }

    public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
        final User user = authentication.getDetails();
        user.setExpires(System.currentTimeMillis() + TEN_DAYS);
        final String jwtToken = tokenHandler.createTokenForUser(user);
        UserToken userToken = new UserToken(jwtToken);
        String tokenJsonBody = JsonUtils.getJson(userToken);
        try {
            response.setContentType("application/json");
            response.getWriter().write(tokenJsonBody);
            response.getWriter().flush();
        } catch (IOException e) {
            throw new SendingTokenException("Could not send token to the client because we couldnt write to the response. There is an IO exception");
        }
    }

    public UserAuthentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
            User user = tokenHandler.parseUserFromToken(token);
            Optional<User> optionalUser = userRepository.findByIdAndDeletedIsFalse(user.getId());
            if(optionalUser.isPresent()){
                return new UserAuthentication(optionalUser.get());
            }
        }
        return null;
    }
}

