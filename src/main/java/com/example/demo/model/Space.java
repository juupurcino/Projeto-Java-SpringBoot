package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Space {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSpace;

    @Column
    private String name;

    // @OneToMany(mappedBy = "space")
    // private Set<Answer> answers = new HashSet<>();

    // @OneToMany(mappedBy = "space")
    // private Set<Question> question = new HashSet<>();

    // @OneToMany(mappedBy = "space")
    // private Set<Permission> permission = new HashSet<>();
    
    @Column
    private Integer qtdUsers;

    public Long getIdSpace() {
        return idSpace;
    }

    public void setIdSpace(Long idSpace) {
        this.idSpace = idSpace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getQtdUsers() {
        return qtdUsers;
    }

    public void setQtdUsers(Integer qtdUsers) {
        this.qtdUsers = qtdUsers;
    }
    
    
}