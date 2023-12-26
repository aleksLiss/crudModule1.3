package org.AleksLis.CrudApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;



@AllArgsConstructor
@Setter
@Getter
public class Writer {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
}
