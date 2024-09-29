package com.sunyesle.spring_boot_batch.repository;

import com.sunyesle.spring_boot_batch.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByViewCountGreaterThanEqual(int viewCount, Pageable pageable);
}
