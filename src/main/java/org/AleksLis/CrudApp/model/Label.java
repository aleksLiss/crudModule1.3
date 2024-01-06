package org.AleksLis.CrudApp.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Label {

    private Long id;
    private String name;
    private StatusEntity labelStatus;


    public Label(Long id, String name) {
        this.id = id;
        this.name = name;
        this.labelStatus = StatusEntity.ACTIVE;
    }
}
