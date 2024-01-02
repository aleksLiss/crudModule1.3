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
import org.AleksLis.CrudApp.repository.util.WriterUtil;
import org.AleksLis.CrudApp.systemMessages.SystemMessages;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class WriterRepositoryImpl implements WriterRepository {

    private Util util;

    @Override
    public Writer getById(Long id) {
        Writer writerFromDB = null;
        String pathFile = Util.PATH + Util.WRITERDB;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFile))) {
            String fromJson = Util.jsonToString(bufferedReader);
            try {
                Type type = new TypeToken<List<Writer>>(){}.getType();
                List<Writer> listWriters = Util.getListWritersFromJson(fromJson, type);
                Util.emptyDb(listWriters.size());
                try {
                    List<Writer> result = listWriters.stream()
                            .filter((writer) -> writer.getId().equals(id))
                            .collect(Collectors.toList());
                    Util.idNotExist(result.size());
                    writerFromDB = result.get(0);
                } catch (IdNotExistException ignored) {}
            } catch (EmptyDBException ignored) {}
        } catch (IOException e) {
            System.out.println(SystemMessages.JSON_NOT_EXIST_EX.getMessage());
        }
        return writerFromDB;
    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writersFromDB = null;
        String pathFile = Util.PATH + Util.WRITERDB;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFile))) {
            String fromJson = Util.jsonToString(bufferedReader);
            try {
                Type type = new TypeToken<List<Writer>>(){}.getType();
                writersFromDB = Util.getListWritersFromJson(fromJson, type);
                Util.emptyDb(writersFromDB.size());
            } catch (EmptyDBException ignored) {}
        } catch (IOException e) {
            System.out.println(SystemMessages.JSON_NOT_EXIST_EX.getMessage());
        }
        return writersFromDB;
    }

    @Override
    public Writer save(Writer writer) {
        Writer writerFromDB = null;
        String pathFile = Util.PATH + Util.WRITERDB;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFile))) {
            String fromJson = Util.jsonToString(bufferedReader);
            try {
                Type type = new TypeToken<List<Writer>>(){}.getType();
                List<Writer> listWriters = Util.getListWritersFromJson(fromJson, type);
                Util.emptyDb(listWriters.size());
                try {
                    List<Writer> result = listWriters.stream()
                            .filter((writer) -> writer.getId().equals(id))
                            .collect(Collectors.toList());
                    Util.idNotExist(result.size());
                    writerFromDB = result.get(0);
                } catch (IdNotExistException ignored) {}
            } catch (EmptyDBException ignored) {}
        } catch (IOException e) {
            System.out.println(SystemMessages.JSON_NOT_EXIST_EX.getMessage());
        }
        return writerFromDB;
    }

    @Override
    public Writer update(Writer writer) {

        String pathFile = WriterUtil.PATH + WriterUtil.WRITERDB;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFile))) {
            String fromJson = WriterUtil.JsonToString(bufferedReader);
            List<Writer> listWriters = WriterUtil.getListWritersFromJson(fromJson, WriterUtil.getTypeOfListWriters());
            try {
                WriterUtil.getWriterById(listWriters, writer.getId());
                listWriters.stream()
                        .peek((a) -> {
                            if (a.getId().equals(writer.getId())) {
                                a.setFirstName(writer.getFirstName());
                                a.setLastName(writer.getLastName());
                                a.setPosts(writer.getPosts());
                                a.setWriterStatus(writer.getWriterStatus());
                            }
                        }).collect(Collectors.toList());
                try (FileWriter fileWriter = new FileWriter(pathFile)) {
                    String listWritersToJson = new Gson().toJson(listWriters);
                    fileWriter.write(listWritersToJson);
                } catch (IOException e) {
                    System.out.println(SystemMessages.WRITE_TO_JSON_EX.getMessage());
                }
            } catch (IdNotExistException e) {
                System.out.println(SystemMessages.ID_NOT_EXIST_EX.getMessage());
            }
        } catch (Exception e) {
            System.out.println(SystemMessages.JSON_NOT_EXIST_EX.getMessage());
        }
        return writer;


    }

    @Override
    public void delete(Long id) {
        String pathFile = WriterUtil.PATH + WriterUtil.WRITERDB;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFile))) {
            String fromJson = util.JsonToString(bufferedReader);
            List<Writer> listWriters = WriterUtil.getListWritersFromJson(fromJson, WriterUtil.getTypeOfListWriters());
            try {
                WriterUtil.getWriterById(listWriters, id);
                listWriters.stream()
                        .peek((a) -> {
                            if (a.getId().equals(id)) {
                                a.setWriterStatus(StatusEntity.DELETE);
                            }
                        }).collect(Collectors.toList());
                try (FileWriter fileWriter = new FileWriter(pathFile)) {
                    String listWritersToJson = new Gson().toJson(listWriters);
                    fileWriter.write(listWritersToJson);
                } catch (IOException e) {
                    System.out.println(SystemMessages.WRITE_TO_JSON_EX.getMessage());
                }
            } catch (IdNotExistException e) {
                System.out.println(SystemMessages.ID_NOT_EXIST_EX.getMessage());
            }
        } catch (Exception e) {
            System.out.println(SystemMessages.JSON_NOT_EXIST_EX.getMessage());
        }
    }
}
