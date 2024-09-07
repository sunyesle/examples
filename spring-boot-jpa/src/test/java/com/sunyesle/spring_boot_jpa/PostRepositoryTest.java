package com.sunyesle.spring_boot_jpa;

import com.sunyesle.spring_boot_jpa.join3.Comment;
import com.sunyesle.spring_boot_jpa.join3.Post;
import com.sunyesle.spring_boot_jpa.join3.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void setUp() {
        Post post1 = new Post("Post1", "..."); // comment 2개
        Post post2 = new Post("Post2", "..."); // comment 1개
        Post post3 = new Post("Post3", "..."); // comment 0개

        Comment comment1 = new Comment("Post1 Comment1");
        Comment comment2 = new Comment("Post1 Comment2");
        Comment comment3 = new Comment("Post2 Comment2");

        post1.addComment(comment1);
        post1.addComment(comment2);
        post2.addComment(comment3);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        entityManager.flush();
    }

    @Test
    void findAllEntityGraphTest() {
        // when
        List<Post> posts = postRepository.findAll();

        // then
        posts.forEach(p -> System.out.println(p.toString()));
        assertThat(posts).hasSize(3);
    }

    @Test
    void findAllLeftJoinFetchTest() {
        // when
        List<Post> posts = postRepository.findAllLeftJoinFetch();

        // then
        posts.forEach(p -> System.out.println(p.toString()));
        assertThat(posts).hasSize(3);
    }

    @Test
    void findAllJoinFetchTest() {
        // when
        List<Post> posts = postRepository.findAllJoinFetch();

        // then
        posts.forEach(p -> System.out.println(p.toString()));
        assertThat(posts).hasSize(2);
    }
}
