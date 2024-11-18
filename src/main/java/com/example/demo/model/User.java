package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="tbUser")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;
    
    @Column
    private String password;
    
    @Column
    private String email;
    
    @Column
    private String edv;

    // @OneToMany(mappedBy = "user")
    // private Set<Answer> answers = new HashSet<>();

    // @OneToMany(mappedBy = "user")
    // private Set<Question> question = new HashSet<>();

    // @OneToMany(mappedBy = "user")
    // private Set<Permission> permission = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEdv() {
        return edv;
    }

    public void setEdv(String edv) {
        this.edv = edv;
    }

    // public Set<Answer> getAnswers() {
    //     return answers;
    // }

    // public void setAnswers(Set<Answer> answers) {
    //     this.answers = answers;
    // }

    // public Set<Question> getQuestion() {
    //     return question;
    // }

    // public void setQuestion(Set<Question> question) {
    //     this.question = question;
    // }

    // public Set<Permission> getPermission() {
    //     return permission;
    // }

    // public void setPermission(Set<Permission> permission) {
    //     this.permission = permission;
    // }
    
}
