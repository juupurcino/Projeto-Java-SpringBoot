package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Question {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String question;

    @Column
    private String title;
    
    // @OneToMany(mappedBy = "question")
    // private Set<Answer> answers = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name = "idSpace")
    private Space space;
    
    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    // public Set<Answer> getAnswers() {
    //     return answers;
    // }
    
    // public void setAnswers(Set<Answer> answers) {
    //     this.answers = answers;
    // }
    
    public Space getSpace() {
        return space;
    }
    
    public void setSpace(Space space) {
        this.space = space;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
}