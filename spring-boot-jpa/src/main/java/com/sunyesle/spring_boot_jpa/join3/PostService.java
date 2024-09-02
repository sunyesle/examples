package com.sunyesle.spring_boot_jpa.join3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post> findAllEntityGraph() {
        List<Post> posts = postRepository.findAll();
        posts.forEach(p -> System.out.println(p.toString()));
        return posts;
    }

    public List<Post> findAllFetchJoin() {
        List<Post> posts = postRepository.findAllFetchJoin();
        posts.forEach(p -> System.out.println(p.toString()));
        return posts;
    }
}
