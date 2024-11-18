package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    @Query("SELECT u FROM Question u WHERE u.title = :searchValue or u.question = :searchValue")
    List<Question> searchQuestion(@Param("searchValue") String searchValue);
    List<Question> findAllBySpaceIdSpace(Long id);
    void deleteAllBySpaceIdSpace(Long id);
}