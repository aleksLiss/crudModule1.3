package org.AleksLis.CrudApp.controller;

import org.AleksLis.CrudApp.model.Writer;
import org.AleksLis.CrudApp.services.WriterService;

import java.util.List;

public class WriterController {

    private final WriterService writerService;

    public WriterController() {
        this.writerService = new WriterService();
    }

    public Writer getById(Long id){
        return writerService.getById(id);
    }

    public List<Writer> getAll(){
        return writerService.getAll();
    }

    public Writer saveWriter (Writer writer){
        return writerService.saveWriter(writer);
    }

    public Writer updateWriter(Writer writer){
        return writerService.updateWriter(writer);
    }

    public void deleteWriter(Long id){
        writerService.deleteWriter(id);
    }
}
