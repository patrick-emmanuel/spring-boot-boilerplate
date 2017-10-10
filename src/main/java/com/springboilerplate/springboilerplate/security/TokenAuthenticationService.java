package com.springboilerplate.springboilerplate.security;

import com.springboilerplate.springboilerplate.model.User;
import com.springboilerplate.springboilerplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.util.Optional;

@Service
public class TokenAuthenticationService {

    public static final String AUTH_HEADER_NAME = "AUTHORIZATION";
    private static final String AUTH_COOKIE_NAME = "AUTH-TOKEN";
    public static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;

    private final TokenHandler tokenHandler;
    private UserRepository userRepository;

    @Autowired
    public TokenAuthenticationService(Environment env, UserRepository userRepository) {
        String secret = env.getProperty("MOBSTAFF_TOKEN_SECRET");
        tokenHandler = new TokenHandler(DatatypeConverter.parseBase64Binary(secret));
        this.userRepository = userRepository;
    }

    public void addAuthentication(HttpServletResponse response,
                                  UserAuthentication authentication) {
        final User user = authentication.getDetails();
        user.setExpires(System.currentTimeMillis() + TEN_DAYS);
        final String token = tokenHandler.createTokenForUser(user);

        // Put the token into a cookie because the client can't capture response
        // headers of redirects / full page reloads.
        // (Its reloaded as a result of this response triggering a redirect back to "/")
        response.addHeader(AUTH_HEADER_NAME, token);
        response.addCookie(createCookieForToken(token));
    }

    public String createUserToken(User user) {
        return tokenHandler.createTokenForUser(user);
    }

    public UserAuthentication getAuthentication(HttpServletRequest request) {
        // to prevent CSRF attacks we still only allow authentication using a custom HTTP header
        // (it is up to the client to read our previously set cookie and put it in the header)
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

    private Cookie createCookieForToken(String token) {
        final Cookie authCookie = new Cookie(AUTH_COOKIE_NAME, token);
        authCookie.setPath("/");
        return authCookie;
    }
}

