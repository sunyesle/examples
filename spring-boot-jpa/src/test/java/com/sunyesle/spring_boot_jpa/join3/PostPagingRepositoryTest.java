package com.sunyesle.spring_boot_jpa.join3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PostPagingRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    PostRepository postRepository;

    private static final int POST_COUNT = 12;
    private static final int COMMENT_MAX_COUNT = 5;

    @BeforeEach
    void setUp() {
        Random random = new Random();

        List<Post> posts = new ArrayList<>();
        for (int i = 1; i <= POST_COUNT; i++) {
            Post post = new Post("post" + i, "content of post " + i, LocalDateTime.of(2024, 1, 1, 12, 0));

            // 랜덤한 갯수의 댓글을 추가한다.
            int numberOfComments = random.nextInt(COMMENT_MAX_COUNT + 1);
            for (int j = 1; j <= numberOfComments; j++) {
                Comment comment = new Comment("comment" + j + " of post" + i);
                post.addComment(comment);
            }
            posts.add(post);
        }

        for (Post post : posts) {
            postRepository.save(post);
        }

        entityManager.flush();
    }

    @Test
    void jpqlQueryPagingTest() {
        List<Post> postPage1 = postRepository.findByTitleLike("%post%", 1, 5);
        List<Post> postPage2 = postRepository.findByTitleLike("%post%", 2, 5);
        List<Post> postPage3 = postRepository.findByTitleLike("%post%", 3, 5);

        assertThat(postPage1).hasSize(5);
        assertThat(postPage2).hasSize(5);
        assertThat(postPage3).hasSize(2);
    }

    @Test
    void DtoMappingTest() {
        List<PostDTO> postPage1 = postRepository.findPostDtoByTitle("%", 1, 5);
        List<PostDTO> postPage2 = postRepository.findPostDtoByTitle("%", 2, 5);
        List<PostDTO> postPage3 = postRepository.findPostDtoByTitle("%", 3, 5);

        System.out.println(postPage1);
        System.out.println(postPage2);
        System.out.println(postPage3);
    }
}
