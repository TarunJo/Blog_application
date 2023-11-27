package com.mountblue.spring.blogApplication.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authorities")
public class Authorities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToMany()
    @JoinTable(
            name = "user_authorities",
            joinColumns = @JoinColumn(name = "role_id"), // field from current class
            inverseJoinColumns=@JoinColumn(name = "user_id") // field from other class
    )
    private List<User> userRole;
    @Column(name = "user_role")
    private String role;

    public Authorities() {}

    public Authorities(String user_role) {
        this.role = user_role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<User> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<User> userRole) {
        this.userRole = userRole;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void addUser(User theUser) {
        if(userRole == null) {
            userRole = new ArrayList<>();
        }

        userRole.add(theUser);
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
