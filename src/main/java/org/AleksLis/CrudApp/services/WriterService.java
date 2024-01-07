package org.AleksLis.CrudApp.services;

import org.AleksLis.CrudApp.model.Writer;
import org.AleksLis.CrudApp.repository.Implements.WriterRepositoryImpl;
import org.AleksLis.CrudApp.repository.WriterRepository;

import java.util.List;

public class WriterService {

    private WriterRepositoryImpl writerRepository;

    public WriterService() {
        this.writerRepository = new WriterRepositoryImpl();
    }

    public Writer getById(Long id) {
        return writerRepository.getById(id);
    }

    ;

    public List<Writer> getAll() {
        return writerRepository.getAll();
    }

    ;

    public Writer saveWriter(Writer writer) {
        return writerRepository.save(writer);
    }

    ;

    public Writer updateWriter(Writer writer) {
        return writerRepository.update(writer);
    }

    ;

    public void deleteWriter(Long id) {
        writerRepository.delete(id);
    }

    ;
}
