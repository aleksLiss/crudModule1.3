package org.AleksLis.CrudApp.repository.Implements;

import com.google.gson.Gson;
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
import java.util.List;
import java.util.stream.Collectors;

public class WriterRepositoryImpl implements WriterRepository {
    @Override
    public Writer getById(Long id) {
        Writer writer = null;
        String pathFile = Util.PATH + Util.WRITERDB;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFile))) {
            String fromJson = Util.JsonToString(bufferedReader);
            List<Writer> listWriters = Util.getListWritersFromJson(fromJson, Util.getTypeOfListWriters());
            try {
                writer = Util.getWriterById(listWriters, id);
            } catch (IdNotExistException e) {
                System.out.println(SystemMessages.ID_NOT_EXIST_EX.getMessage());
            }
        } catch (Exception e) {
            System.out.println(SystemMessages.JSON_NOT_EXIST_EX.getMessage());
        }
        return writer;
    }

    @Override
    public List<Writer> getAll() {
        String pathFile = Util.PATH + Util.WRITERDB;
        List<Writer> listWriters = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFile))) {
            String fromJson = Util.JsonToString(bufferedReader);
            listWriters = Util.getListWritersFromJson(fromJson, Util.getTypeOfListWriters());
        } catch (Exception e) {
            System.out.println(SystemMessages.JSON_NOT_EXIST_EX.getMessage());
        }
        return listWriters;
    }

    @Override
    public Writer save(Writer writer) {
        String pathFile = Util.PATH + Util.WRITERDB;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFile))) {
            String fromJson = Util.JsonToString(bufferedReader);
            List<Writer> listWriters = Util.getListWritersFromJson(fromJson, Util.getTypeOfListWriters());
            try {
                Util.isIdExist(listWriters, writer.getId());
                listWriters.add(writer);
                try (FileWriter fileWriter = new FileWriter(pathFile)) {
                    String listWritersToJson = new Gson().toJson(listWriters);
                    fileWriter.write(listWritersToJson);
                } catch (IOException e) {
                    System.out.println(SystemMessages.WRITE_TO_JSON_EX.getMessage());
                }
            } catch (IdExistException e) {
                System.out.println(SystemMessages.ID_ALREADY_EXIST.getMessage());
            }
        } catch (Exception e) {
            System.out.println(SystemMessages.JSON_NOT_EXIST_EX.getMessage());
        }
        return writer;
    }

    @Override
    public Writer update(Writer writer) {

        String pathFile = Util.PATH + Util.WRITERDB;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFile))) {
            String fromJson = Util.JsonToString(bufferedReader);
            List<Writer> listWriters = Util.getListWritersFromJson(fromJson, Util.getTypeOfListWriters());
            try {
                Util.getWriterById(listWriters, writer.getId());
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
        String pathFile = Util.PATH + Util.WRITERDB;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFile))) {
            String fromJson = Util.JsonToString(bufferedReader);
            List<Writer> listWriters = Util.getListWritersFromJson(fromJson, Util.getTypeOfListWriters());
            try {
                Util.getWriterById(listWriters, id);
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
