package com.sunyesle.spring_boot_jpa;

import com.sunyesle.spring_boot_jpa.join3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostService postService;

    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        postRepository.flush();

        Post post1 = new Post("Java Thread", "...");
        Post post2 = new Post("Spring Data JPA", "...");
        Post post3 = new Post("Spring Security", "...");

        Comment comment1 = new Comment("comment1");
        Comment comment2 = new Comment("comment2");
        Comment comment3 = new Comment("comment3");

        post1.addComment(comment1);
        post1.addComment(comment2);
        post2.addComment(comment3);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
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

    Post(id=4, title=Java Thread, content=..., comments=[Comment(id=4, content=comment1), Comment(id=5, content=comment2)])
    Post(id=5, title=Spring Data JPA, content=..., comments=[Comment(id=6, content=comment3)])
    Post(id=6, title=Spring Security, content=..., comments=[])
     */
    @Test
    void findAllEntityGraphTest() {
        List<Post> posts = postService.findAllEntityGraph();
        System.out.println("findAllEntityGraph size(): " + posts.size());
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

    Post(id=1, title=Java Thread, content=..., comments=[Comment(id=1, content=comment1), Comment(id=2, content=comment2)])
    Post(id=2, title=Spring Data JPA, content=..., comments=[Comment(id=3, content=comment3)])
     */
    @Test
    void findAllFetchJoinTest() {
        List<Post> posts = postService.findAllFetchJoin();
        System.out.println("findAllFetchJoin size(): " + posts.size());
    }
}
 