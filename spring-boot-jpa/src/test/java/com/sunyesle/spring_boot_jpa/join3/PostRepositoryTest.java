package com.sunyesle.spring_boot_jpa.join3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void setUp() {
        Post post1 = new Post("Post1", "...", LocalDateTime.now()); // comment 2개
        Post post2 = new Post("Post2", "...", LocalDateTime.now()); // comment 1개
        Post post3 = new Post("Post3", "...", LocalDateTime.now()); // comment 0개

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
        entityManager.clear();
    }

    /*
    select
        p1_0.id,
        c1_0.post_id,
        c1_0.id,
        c1_0.content,
        p1_0.content,
        p1_0.title
    from
        post p1_0
    left join
        comment c1_0
            on p1_0.id=c1_0.post_id

    Post(id=1, title=Post1, content=..., comments=[Comment(id=1, content=Post1 Comment1), Comment(id=2, content=Post2 Comment1)])
    Post(id=2, title=Post2, content=..., comments=[Comment(id=3, content=Post2 Comment2)])
    Post(id=3, title=Post3, content=..., comments=[])
     */
    @Test
    void findAllEntityGraphTest() {
        // when
        List<Post> posts = postRepository.findAll();

        // then
        posts.forEach(p -> System.out.println(p.toString()));
        assertThat(posts).hasSize(3);
    }

    /*
    select
        p1_0.id,
        c1_0.post_id,
        c1_0.id,
        c1_0.content,
        p1_0.content,
        p1_0.title
    from
        post p1_0
    left join
        comment c1_0
            on p1_0.id=c1_0.post_id

    Post(id=1, title=Post1, content=..., comments=[Comment(id=1, content=Post1 Comment1), Comment(id=2, content=Post1 Comment2)])
    Post(id=2, title=Post2, content=..., comments=[Comment(id=3, content=Post2 Comment2)])
    Post(id=3, title=Post3, content=..., comments=[])
     */
    @Test
    void findAllLeftJoinFetchTest() {
        // when
        List<Post> posts = postRepository.findAllLeftJoinFetch();

        // then
        posts.forEach(p -> System.out.println(p.toString()));
        assertThat(posts).hasSize(3);
    }

    /*
    select
        p1_0.id,
        c1_0.post_id,
        c1_0.id,
        c1_0.content,
        p1_0.content,
        p1_0.title
    from
        post p1_0
    join
        comment c1_0
            on p1_0.id=c1_0.post_id

    Post(id=1, title=Post1, content=..., comments=[Comment(id=1, content=Post1 Comment1), Comment(id=2, content=Post1 Comment2)])
    Post(id=2, title=Post2, content=..., comments=[Comment(id=3, content=Post2 Comment2)])
     */
    @Test
    void findAllJoinFetchTest() {
        // when
        List<Post> posts = postRepository.findAllJoinFetch();

        // then
        posts.forEach(p -> System.out.println(p.toString()));
        assertThat(posts).hasSize(2);
    }

    @Test
    void queryMethodWithEntityGraphTest() {
        // when
        List<Post> posts = postRepository.findByTitle("Post1");

        // then
        posts.forEach(p -> System.out.println(p.toString()));
        assertThat(posts).hasSize(1);
    }

    @Test
    void jpqlWithEntityGraphTest() {
        // when
        List<Post> posts = postRepository.findByTitleJpql("Post1");

        // then
        posts.forEach(p -> System.out.println(p.toString()));
        assertThat(posts).hasSize(1);
    }
}
