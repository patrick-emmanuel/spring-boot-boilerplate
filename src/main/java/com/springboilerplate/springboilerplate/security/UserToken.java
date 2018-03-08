package com.springboilerplate.springboilerplate.security;

public class UserToken {
    private String jwtToken;

    public UserToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
