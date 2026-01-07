package com.sunyesle.spring_boot_jpa.join3;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository {

    @Query("""
            SELECT p
            FROM Post p
            LEFT JOIN FETCH p.comments
            WHERE p.id IN (
                SELECT id
                FROM (
                    SELECT id AS id,
                        ROW_NUMBER() OVER (ORDER BY createdAt ASC) AS ranking
                    FROM Post
                    WHERE title LIKE :title
                ) pr
                WHERE ranking BETWEEN (:page - 1) * :pageSize + 1 AND :page * :pageSize
            )
            """)
    List<Post> findByTitleLike(String title, int page, int pageSize);
}
