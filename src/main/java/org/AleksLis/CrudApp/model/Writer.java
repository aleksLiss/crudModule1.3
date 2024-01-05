package org.AleksLis.CrudApp.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;



@Setter
@Getter
public class Writer {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    private StatusEntity writerStatus;

    public Writer(Long id, String firstName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = new ArrayList<>();
        this.writerStatus = StatusEntity.ACTIVE;
    }
}
