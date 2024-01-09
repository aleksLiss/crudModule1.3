package org.AleksLis.CrudApp.model;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Post {
    private Long id;
    private String content;
    private LocalDate created;
    private LocalDate updated;
    private List<Label> labels;
    private StatusEntity postStatus;
    public Post(Long id, String content) {
        this.id = id;
        this.content = content;
        this.created = LocalDate.now();
        this.updated = LocalDate.now();
        this.labels = new ArrayList<>();
        this.postStatus = StatusEntity.ACTIVE;
    }
}
