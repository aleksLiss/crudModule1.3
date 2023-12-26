package org.AleksLis.CrudApp.controller;

import org.AleksLis.CrudApp.model.Label;
import org.AleksLis.CrudApp.services.LabelService;

import java.util.List;

public class LabelController {
    private LabelService labelService;

    public Label getById(Long id){
        return labelService.getById(id);
    };

    public List<Label> getAll(){
        return labelService.getAll();
    };

    public Label saveLabel(Label label){
        return labelService.saveLabel(label);
    };

    public Label updateLabel(Label label){
        return labelService.updateLabel(label);
    };

    public void deleteById(Long id){
        labelService.deleteById(id);
    };
}
