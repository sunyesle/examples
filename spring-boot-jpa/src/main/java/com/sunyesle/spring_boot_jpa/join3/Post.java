package com.sunyesle.spring_boot_jpa.join3;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Comment> comments = new ArrayList<>();

    public Post() {
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addComment(Comment comment) {
        comment.setPost(this);
        comments.add(comment);
    }
}
