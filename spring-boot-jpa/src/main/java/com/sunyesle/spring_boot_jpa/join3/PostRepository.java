package com.sunyesle.spring_boot_jpa.join3;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Override
    @EntityGraph(attributePaths = "comments")
    List<Post> findAll();

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.comments")
    List<Post> findAllLeftJoinFetch();

    @Query("SELECT p FROM Post p JOIN FETCH p.comments")
    List<Post> findAllJoinFetch();
}
