package com.sunyesle.spring_boot_jpa.join3;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString(exclude = "post")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment() {
    }

    public Comment(String content) {
        this.content = content;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
