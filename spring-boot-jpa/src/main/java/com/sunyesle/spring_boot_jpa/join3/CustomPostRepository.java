package com.sunyesle.spring_boot_jpa.join3;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomPostRepository {
    List<PostDTO> findPostDtoByTitle(String title, int page, int pageSize);
}
