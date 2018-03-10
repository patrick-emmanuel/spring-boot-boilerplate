package com.springboilerplate.app.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.springboilerplate.app.userRole.UserRole;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Where;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="users")
@Indexed
@Where(clause = "deleted = false")
@JsonRootName(value = "payload")
public class User implements UserDetails{
    @Transient
    private LocalDateTime now = LocalDateTime.now();
    private Long id;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private List<UserRole> userRoles = new ArrayList<>();
    private Date lastPasswordResetDate;
    private LocalDateTime lastLogin = now;
    private LocalDateTime createdAt = now;
    private LocalDateTime modifiedAt = now;
    private boolean enabled = true;
    private boolean deleted = false;

    public User(String firstname, String lastname, String password, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
    }

    public User(String firstname, String lastname, String password, String email, List<UserRole> userRoles) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.userRoles = userRoles;
    }

    public User() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @DocumentId
    @Column(name="user_id", unique = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Size(min=2, max=30, message="The length of firstname should be within the range of 2 to 30.")
    @Column(name = "firstname")
    @Field(index= Index.YES, analyze= Analyze.YES, store= Store.NO)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @NotNull
    @Size(min=2, max=30, message="The length lastname should be within the range of 2 to 30.")
    @Column(name = "lastname")
    @Field(index= Index.YES, analyze= Analyze.YES, store= Store.NO)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @NotNull
    @Size(min=2, max=100, message="The length of password should be within the range of 2 to 30.")
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull
    @Column(name = "email", unique = true)
    @Email(message = "Email is not valid.")
    @Field(index= Index.YES, analyze= Analyze.YES, store= Store.NO)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    @OneToMany(mappedBy="user", orphanRemoval = true, fetch = FetchType.EAGER)
    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public void addUserRole(UserRole userRole){
        userRoles.add(userRole);
        userRole.setUser(this);
    }

    @Column(name = "enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name = "created_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "modified_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Column(name = "deleted")
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Column(name = "last_login")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Column(name = "last_password_reset_data")
    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @Override
    @JsonIgnore
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Hibernate initialize because role on userRole is lazily loaded.
        userRoles.forEach(userRole -> Hibernate.initialize(userRole.getRole()));
        return userRoles.stream().map(userRole -> new SimpleGrantedAuthority(
                        userRole.getRole().getName().name())).collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    @Transient
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }
}