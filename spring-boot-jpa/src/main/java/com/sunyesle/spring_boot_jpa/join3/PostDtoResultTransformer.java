package com.sunyesle.spring_boot_jpa.join3;

import org.hibernate.transform.ResultTransformer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PostDtoResultTransformer implements ResultTransformer<PostDTO> {
    private final Map<Long, PostDTO> postDtoMap = new LinkedHashMap<>();

    @Override
    public PostDTO transformTuple(Object[] tuple, String[] aliases) {
        Long postId = Long.valueOf(tuple[0].toString());
        String postTitle = (String) tuple[1];
        Long commentId = Long.valueOf(tuple[2].toString());
        String commentContent = (String) tuple[3];

        PostDTO postDTO = postDtoMap.computeIfAbsent(postId, id -> new PostDTO(id, postTitle));

        CommentDTO commentDTO = new CommentDTO(commentId, commentContent);
        postDTO.getComments().add(commentDTO);

        return postDTO;
    }

    @Override
    public List<PostDTO> transformList(List collection) {
        return new ArrayList<>(postDtoMap.values());
    }
}
