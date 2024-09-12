package com.sunyesle.spring_boot_jpa.join3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDTO {

    public static final String ID_ALIAS = "p_id";
    public static final String TITLE_ALIAS = "p_title";

    private Long id;
    private String title;
    private Set<CommentDTO> comments = new LinkedHashSet<>();

    public PostDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public PostDTO(Object[] tuples, Map<String, Integer> aliasToIndexMap) {
        this.id = Long.valueOf(tuples[aliasToIndexMap.get(ID_ALIAS)].toString());
        this.title = tuples[aliasToIndexMap.get(TITLE_ALIAS)].toString();
    }
}
