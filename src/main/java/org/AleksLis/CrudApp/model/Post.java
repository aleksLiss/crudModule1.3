package org.AleksLis.CrudApp.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@Getter
@Setter
public class Post {
    private Long id;
    private String content;
    private LocalDate created;
    private LocalDate updated;
    private List<Label> labels;


}
