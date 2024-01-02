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
        String pathFile = Util.PATH + Util.WRITERDB;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFile))) {
            String fromJson = Util.jsonToString(bufferedReader);
            try {
                Type type = new TypeToken<List<Writer>>() {
                }.getType();
                List<Writer> listWriters = new Gson().fromJson(fromJson, type);
                Util.emptyDb(listWriters.size());
                try {
                    List<Writer> result = listWriters.stream()
                            .filter((writer) -> writer.getId().equals(id))
                            .collect(Collectors.toList());
                    Util.idNotExist(result.size());
                    writerFromDB = result.get(0);
                } catch (IdNotExistException ignored) {
                }
            } catch (EmptyDBException ignored) {
            }
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
                Type type = new TypeToken<List<Writer>>() {
                }.getType();
                writersFromDB = new Gson().fromJson(fromJson, type);
                Util.emptyDb(writersFromDB.size());
            } catch (EmptyDBException ignored) {
            }
        } catch (IOException e) {
            System.out.println(SystemMessages.JSON_NOT_EXIST_EX.getMessage());
        }
        return writersFromDB;
    }

    @Override
    public Writer save(Writer writer) {
        String pathFile = Util.PATH + Util.WRITERDB;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFile))) {
            String fromJson = Util.jsonToString(bufferedReader);
            Type type = new TypeToken<List<Writer>>() {
            }.getType();
            List<Writer> listWriters = new Gson().fromJson(fromJson, type);
            try {
                List<Writer> result = listWriters.stream()
                        .filter((wr) -> wr.getId().equals(writer.getId()))
                        .collect(Collectors.toList());
                Util.idExist(result.size());
                result.add(writer);
                try (FileWriter fileWriter = new FileWriter(pathFile)) {
                    String listWritersToJson = new Gson().toJson(result);
                    fileWriter.write(listWritersToJson);
                } catch (IOException e) {
                    System.out.println(SystemMessages.WRITE_TO_JSON_EX.getMessage());
                }
            } catch (IdExistException ignored) {
            }
        } catch (IOException e) {
            System.out.println(SystemMessages.JSON_NOT_EXIST_EX.getMessage());
        }
        return writer;
    }


    @Override
    public Writer update(Writer writer) {
        Writer writerFromDB = null;
        String pathFile = Util.PATH + Util.WRITERDB;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFile))) {
            String fromJson = Util.jsonToString(bufferedReader);
            try {
                Type type = new TypeToken<List<Writer>>() {
                }.getType();
                List<Writer> listWriters = new Gson().fromJson(fromJson, type);
                Util.emptyDb(listWriters.size());
                try {
                    List<Writer> result = listWriters.stream()
                            .filter((wr) -> wr.getId().equals(writer.getId()))
                            .collect(Collectors.toList());
                    Util.idNotExist(result.size());
                    writerFromDB = result.get(0);
                    writerFromDB.setFirstName(writer.getFirstName());
                    writerFromDB.setLastName(writer.getLastName());
                    writerFromDB.setPosts(writer.getPosts());
                    writerFromDB.setWriterStatus(writer.getWriterStatus());
                    result.add(writerFromDB);
                    try (FileWriter fileWriter = new FileWriter(pathFile)) {
                        String listWritersToJson = new Gson().toJson(result);
                        fileWriter.write(listWritersToJson);
                    } catch (IOException e) {
                        System.out.println(SystemMessages.WRITE_TO_JSON_EX.getMessage());
                    }
                } catch (IdNotExistException ignored) {
                }
            } catch (EmptyDBException ignored) {
            }
        } catch (IOException e) {
            System.out.println(SystemMessages.JSON_NOT_EXIST_EX.getMessage());
        }
        return writerFromDB;
    }

    @Override
    public void delete(Long id) {
        Writer writer = null;
        String pathFile = Util.PATH + Util.WRITERDB;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFile))) {
            String fromJson = Util.jsonToString(bufferedReader);
            try {
                Type type = new TypeToken<List<Writer>>() {
                }.getType();
                List<Writer> listWriters = new Gson().fromJson(fromJson, type);
                Util.emptyDb(listWriters.size());
                try {
                    List<Writer> result = listWriters.stream()
                            .filter((wr) -> wr.getId().equals(writer.getId()))
                            .collect(Collectors.toList());
                    Util.idNotExist(result.size());
                    Writer writerFromDB = result.get(0);
                    writerFromDB.setWriterStatus(StatusEntity.DELETE);
                    result.add(writerFromDB);
                    try (FileWriter fileWriter = new FileWriter(pathFile)) {
                        String listWritersToJson = new Gson().toJson(result);
                        fileWriter.write(listWritersToJson);
                    } catch (IOException e) {
                        System.out.println(SystemMessages.WRITE_TO_JSON_EX.getMessage());
                    }
                } catch (IdNotExistException ignored) {
                }
            } catch (EmptyDBException ignored) {
            }
        } catch (IOException e) {
            System.out.println(SystemMessages.JSON_NOT_EXIST_EX.getMessage());
        }
    }
}
