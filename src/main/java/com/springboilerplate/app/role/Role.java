package com.springboilerplate.app.role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.springboilerplate.app.userRole.UserRole;
import org.hibernate.search.annotations.DocumentId;

@Entity
@Table(name="role")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Role.class)
@JsonRootName(value = "payload")
public class Role {
    @Transient
    private LocalDateTime now = LocalDateTime.now();

    private Long id;
    private RoleType name;
    private List<UserRole> userRoles = new ArrayList<>();
    private boolean enabled = true;
    private boolean deleted = false;
    private LocalDateTime createdAt = now;
    private LocalDateTime modifiedAt = now;

    public Role(RoleType name) {
        this.name = name;
    }

    public Role() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @DocumentId
    @Column(name="role_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Column(name="name", unique = true, length = 50)
    @Enumerated(EnumType.STRING)
    public RoleType getName() {
        return name;
    }

    public void setName(RoleType name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "role", orphanRemoval = true)
    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public void addUserRole(UserRole userRole){
        userRoles.add(userRole);
        userRole.setRole(this);
    }

    @Column
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Column
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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
}
