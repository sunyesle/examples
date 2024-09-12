package com.sunyesle.spring_boot_jpa.join3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentDTO {
    public static final String ID_ALIAS = "pc_id";
    public static final String CONTENT_ALIAS = "pc_content";

    private Long id;
    private String content;

    public CommentDTO(Object[] tuples, Map<String, Integer> aliasToIndexMap) {
        this.id = Long.valueOf(tuples[aliasToIndexMap.get(ID_ALIAS)].toString());
        this.content = tuples[aliasToIndexMap.get(CONTENT_ALIAS)].toString();
    }
}
