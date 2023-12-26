package org.AleksLis.CrudApp.repository.Implements;

import org.AleksLis.CrudApp.model.Writer;
import org.AleksLis.CrudApp.repository.WriterRepository;
import org.AleksLis.CrudApp.repository.util.JsonUtil;

import java.util.List;

public class WriterRepositoryImpl implements WriterRepository {

    private JsonUtil jsonUtil;

    @Override
    public Writer getById(Long id) {
        return jsonUtil.getWriter(id);
    }

    @Override
    public List<Writer> getAll() {
        return jsonUtil.getAll();
    }

    @Override
    public Writer save(Writer writer) {
        return jsonUtil.saveWriterToJson(writer);
    }

    @Override
    public Writer update(Writer writer) {
        return null;
    }

    @Override
    public void delete(Long id) {
        jsonUtil.deleteWriter(id);
    }
}
