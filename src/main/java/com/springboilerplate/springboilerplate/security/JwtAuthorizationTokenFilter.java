package com.springboilerplate.springboilerplate.security;

import com.springboilerplate.springboilerplate.exceptions.ExpiredTokenException;
import com.springboilerplate.springboilerplate.exceptions.InvalidTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationTokenFilter extends OncePerRequestFilter{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private String tokenHeader;

    public JwtAuthorizationTokenFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, String tokenHeader) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.tokenHeader = tokenHeader;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        logger.debug("Processing authentication for " + httpServletRequest.getRequestURL());
        final String requestHeader = httpServletRequest.getHeader(this.tokenHeader);
        String email = null, authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
            email = parseUser(authToken);
        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }
        logger.debug("checking authentication for user " + email);
        setAuthentication(httpServletRequest, email, authToken);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void setAuthentication(HttpServletRequest httpServletRequest, String email, String authToken) {
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.debug("security context was null, so authorizing user");

            // It is not compelling necessary to load the use details from the database.
            // You could also store the information in the token and read it from it. It's up to you ;)
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            // For simple validation it is completely sufficient to just check the token integrity.
            // You don't have to call the database compellingly. Again it's up to you ;)
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                logger.info("Authorized user '{}', setting security context" + email);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
    }

    private String parseUser(String authToken) {
        String email;
        try {
            email = jwtTokenUtil.getEmailFromToken(authToken);
        } catch (IllegalArgumentException e) {
            logger.error("an error occured during getting email from token", e);
            throw new InvalidTokenException("Error parsing the username from the token");
        } catch (ExpiredJwtException e) {
            logger.warn("the token is expired and not valid anymore", e);
            throw new ExpiredTokenException("The token is not valid");
        }
        return email;
    }
}
