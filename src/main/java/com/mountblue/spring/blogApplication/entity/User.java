package com.mountblue.spring.blogApplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "user_name")
    @NotNull
    private String userName;
    @Column(name = "email")
    @NotNull
    private String email;
    @JsonIgnore
    @Column(name = "password")
    @NotNull
    private String password;
    @Column(name = "is_active")
    private boolean isActive;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userComment",
            cascade = CascadeType.ALL)
    private List<Comment> comments;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userPost",
            cascade = CascadeType.ALL)
    private List<Post> posts;
    @ManyToMany()
    @JoinTable(
            name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id"), // field from current class
            inverseJoinColumns=@JoinColumn(name = "role_id") // field from other class
    )
    private List<Authorities> authorities;

    public User() {}

    public User(String name, String email, String password) {
        this.userName = name;
        this.email = email;
        this.password = password;
        this.isActive = true;
    }

    public List<Authorities> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authorities> authorities) {
        this.authorities = authorities;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Post> getPost() {
        return posts;
    }

    public void setPost(List<Post> post) {
        this.posts = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addPost(Post thePost) {
        if(posts == null) {
            posts = new ArrayList<>();
        }

        posts.add(thePost);
    }

    public void addComment(Comment theComment) {
        if(comments == null) {
            comments = new ArrayList<>();
        }

        comments.add(theComment);
    }

    public void addAuthorities(Authorities theAuthorities) {
        if(authorities == null) {
            authorities = new ArrayList<>();
        }

        authorities.add(theAuthorities);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
