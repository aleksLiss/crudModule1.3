package org.AleksLis.CrudApp.repository.Implements;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.AleksLis.CrudApp.exceptions.EmptyDBException;
import org.AleksLis.CrudApp.exceptions.IdExistException;
import org.AleksLis.CrudApp.exceptions.IdNotExistException;
import org.AleksLis.CrudApp.model.StatusEntity;
import org.AleksLis.CrudApp.model.Writer;
import org.AleksLis.CrudApp.repository.WriterRepository;
import org.AleksLis.CrudApp.repository.util.Util;
import org.AleksLis.CrudApp.systemMessages.SystemMessages;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class WriterRepositoryImpl implements WriterRepository {
    @Override
    public Writer getById(Long id) {
        Writer writerFromDB = null;
        try {
            List<Writer> listWriters = getListWritersFromJsonString(getStringFromJson());
            Util.emptyDb(listWriters.size());
            try {
                List<Writer> result = filterListWritersById(id, listWriters);
                Util.idNotExist(result.size());
                writerFromDB = result.get(0);
            } catch (IdNotExistException ignored) {
            }
        } catch (EmptyDBException ignored) {
        }
        return writerFromDB;
    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writersFromDB = null;
        try {
            writersFromDB = getListWritersFromJsonString(getStringFromJson());
            Util.emptyDb(writersFromDB.size());
        } catch (EmptyDBException ignored) {
        }
        return writersFromDB;
    }

    @Override
    public Writer save(Writer writer) {
        String pathFile = getPathFile();
        List<Writer> listWriters = getListWritersFromJsonString(getStringFromJson());
        try {
            List<Writer> result = filterListWritersById(writer.getId(), listWriters);
            Util.idExist(result.size());
            result.add(writer);
            writeListOfWritersToDB(pathFile, result);
        } catch (IdExistException ignored) {
        }
        return writer;
    }


    @Override
    public Writer update(Writer writer) {
        Writer writerFromDB = null;
        String pathFile = getPathFile();
        try {
            List<Writer> listWriters = getListWritersFromJsonString(getStringFromJson());
            Util.emptyDb(listWriters.size());
            try {
                List<Writer> result = filterListWritersById(writer.getId(), listWriters);
                Util.idNotExist(result.size());
                writerFromDB = updateWriter(result.get(0), writer);
                result.add(writerFromDB);
                writeListOfWritersToDB(pathFile, result);
            } catch (IdNotExistException ignored) {
            }
        } catch (EmptyDBException ignored) {
        }
        return writerFromDB;
    }

    @Override
    public void delete(Long id) {
        String pathFile = getPathFile();
        try {
            List<Writer> listWriters = getListWritersFromJsonString(getStringFromJson());
            Util.emptyDb(listWriters.size());
            try {
                List<Writer> result = filterListWritersById(id, listWriters);
                Util.idNotExist(result.size());
                Writer writerFromDB = result.get(0);
                writerFromDB.setWriterStatus(StatusEntity.DELETE);
                result.add(writerFromDB);
                writeListOfWritersToDB(pathFile, result);
            } catch (IdNotExistException ignored) {
            }
        } catch (EmptyDBException ignored) {
        }
    }

    private List<Writer> filterListWritersById(Long id, List<Writer> writers) {
        return writers.stream()
                .filter((wr) -> wr.getId().equals(id))
                .collect(Collectors.toList());
    }

    private List<Writer> getListWritersFromJsonString(String fromJson) {
        Type type = new TypeToken<List<Writer>>() {
        }.getType();
        return new Gson().fromJson(fromJson, type);
    }

    private Writer updateWriter(Writer writerFromDB, Writer updateWriter) {
        writerFromDB.setFirstName(updateWriter.getFirstName());
        writerFromDB.setLastName(updateWriter.getLastName());
        writerFromDB.setPosts(updateWriter.getPosts());
        writerFromDB.setWriterStatus(updateWriter.getWriterStatus());
        return updateWriter;
    }

    private void writeListOfWritersToDB(String pathFile, List<Writer> writers) {
        try (FileWriter fileWriter = new FileWriter(pathFile)) {
            String listWritersToJson = new Gson().toJson(writers);
            fileWriter.write(listWritersToJson);
        } catch (IOException e) {
            System.out.println(SystemMessages.WRITE_TO_JSON_EX.getMessage());
        }
    }

    private String getPathFile() {
        return Util.PATH + Util.WRITERDB;
    }

    private String getStringFromJson() {
        String fromJson = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(getPathFile()))) {
            fromJson = Util.jsonToString(bufferedReader);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return fromJson;
    }
}
