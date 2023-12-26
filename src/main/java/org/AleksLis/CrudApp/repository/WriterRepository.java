package org.AleksLis.CrudApp.repository;

import org.AleksLis.CrudApp.model.Writer;

import java.util.List;

public class WriterRepository {

    public Writer getById(Long id){};

    public List<Writer> getAll(){};

    public Writer saveWriter(Writer writer){};

    public Writer updateWriter(Writer writer){};

    public void deleteWriter(Long id){};
}
