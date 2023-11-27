package com.mountblue.spring.blogApplication.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "authorities")
public class Authorities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User userRole;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_role")
    private String role;

    public Authorities() {}

    public Authorities(String userName, String role) {
        this.role = role;
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserRole() {
        return userRole;
    }

    public void setUserRole(User userRole) {
        this.userRole = userRole;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Authorities{" +
                "id=" + id +
                ", user=" + userRole +
                ", role='" + role + '\'' +
                '}';
    }
}
