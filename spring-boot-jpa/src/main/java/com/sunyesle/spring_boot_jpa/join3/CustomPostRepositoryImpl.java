package com.sunyesle.spring_boot_jpa.join3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class CustomPostRepositoryImpl implements CustomPostRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PostDTO> findPostDtoByTitle(String title, int page, int pageSize) {
        return entityManager.createNativeQuery("""
                        SELECT p.id AS p_id,
                               p.title AS p_title,
                               pc.id AS pc_id,
                               pc.content AS pc_content
                        FROM post p
                        JOIN comment pc ON p.id = pc.post_id
                        WHERE p.id IN (
                            SELECT pr.id
                            FROM (
                                SELECT
                                    id AS id,
                                    ROW_NUMBER() OVER (ORDER BY id ASC) AS ranking
                                FROM Post
                                WHERE title LIKE :title
                            ) pr
                            WHERE ranking BETWEEN (:page - 1) * :pageSize + 1 AND :page * :pageSize
                        )
                        """)
                .setParameter("title", title)
                .setParameter("page", page)
                .setParameter("pageSize", pageSize)
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new PostDtoResultTransformer())
                .getResultList();
    }
}
