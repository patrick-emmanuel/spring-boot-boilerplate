package com.springboilerplate.springboilerplate.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {

    private Long id;
    private String token;
    private User user;
    private Instant expiryDate = Instant.now().plusSeconds(86400L);

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "password_reset_token_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "expiry_date")
    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
