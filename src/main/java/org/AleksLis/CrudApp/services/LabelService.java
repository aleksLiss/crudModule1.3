package org.AleksLis.CrudApp.services;

import org.AleksLis.CrudApp.model.Label;
import org.AleksLis.CrudApp.repository.LabelRepository;

import java.util.List;

public class LabelService {
    private LabelRepository labelRepository;

    public Label getById(Long id){
        return labelRepository.getById(id);
    };

    public List<Label> getAll(){
        return labelRepository.getAll();
    };

    public Label saveLabel(Label label){
        return labelRepository.saveLabel(label);
    };

    public Label updateLabel(Label label){
        return labelRepository.saveLabel(label);
    };

    public void deleteById(Long id){
        labelRepository.deleteById(id);
    };
}
