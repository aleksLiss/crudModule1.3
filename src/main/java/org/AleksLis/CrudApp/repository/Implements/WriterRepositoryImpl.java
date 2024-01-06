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

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WriterRepositoryImpl implements WriterRepository {

    @Override
    public Writer getById(Long id) {
        Writer writer = null;
        try {
            List<Writer> listWriters = getListWritersFromDB(getStringFromJson());
            throwEmptyDb(listWriters);
            try {
                List<Writer> writers = throwIdNotExist(listWriters, id);
                writer = writers.get(0);
            } catch (IdNotExistException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (EmptyDBException ex) {
            System.out.println(ex.getMessage());
        }
        return writer;
    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writersFromDB = null;
        try {
            writersFromDB = getListWritersFromDB(getStringFromJson());
            throwEmptyDb(writersFromDB);
        } catch (EmptyDBException ex) {
            System.out.println(ex.getMessage());
        }
        return writersFromDB;
    }

    @Override
    public Writer save(Writer writer) {
        try {
            String pathFile = getPathFile();
            File file = new File(pathFile);
            if (file.length() == 0) {
                try {
                    List<Writer> listWriters = new ArrayList<>();
                    listWriters.add(writer);
                    writeListOfWritersToDB(pathFile, listWriters);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                try {
                    List<Writer> listWriters = getListWritersFromDB(getStringFromJson());
                    List<Writer> result = throwIdExist(listWriters, writer);
                    result.add(writer);
                    writeListOfWritersToDB(getPathFile(), result);
                } catch (IdExistException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return writer;
    }

    @Override
    public void delete(Long id) {
        String pathFile = getPathFile();
        try {
            List<Writer> listWriters = getListWritersFromDB(getStringFromJson());
            throwEmptyDb(listWriters);
            try {
                List<Writer> result = throwIdNotExist(listWriters, id);
                Writer writerFromDB = result.get(0);
                writerFromDB.setWriterStatus(StatusEntity.DELETE);
                result.add(writerFromDB);
                writeListOfWritersToDB(pathFile, result);
            } catch (IdNotExistException ignored) {
            }
        } catch (EmptyDBException ignored) {
        }
    }


    @Override
    public Writer update(Writer writer) {
        Writer writerResult = null;
        try {
            List<Writer> listWriters = getListWritersFromDB(getStringFromJson());
            throwEmptyDb(listWriters);
            try {
                List<Writer> filterWritersById = throwIdNotExist(listWriters, writer.getId());
                filterWritersById.remove(0);
                filterWritersById.add(writer);
                writerResult = filterWritersById.get(0);
                writeListOfWritersToDB(getPathFile(), filterWritersById);
            } catch (IdNotExistException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (EmptyDBException ex) {
            System.out.println(ex.getMessage());
        }
        return writerResult;
    }
    private List<Writer> getListWritersFromDB(String fromJson) {
        Type type = new TypeToken<List<Writer>>() {
        }.getType();
        return new Gson().fromJson(fromJson, type);

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

    private void writeListOfWritersToDB(String pathFile, List<Writer> writers) {
        try (FileWriter fileWriter = new FileWriter(pathFile)) {
            String listWritersToJson = new Gson().toJson(writers);
            fileWriter.write(listWritersToJson);
        } catch (IOException e) {
            System.out.println(SystemMessages.WRITE_TO_JSON_EX.getMessage());
        }
    }

    private static void throwEmptyDb(List<Writer> writers) throws EmptyDBException {
        if (writers == null) {
            throw new EmptyDBException(SystemMessages.EMPTY_DB_EX.getMessage());
        }
    }

    private static List<Writer> throwIdNotExist(List<Writer> writers, Long id) throws IdNotExistException {
        List<Writer> result = writers.stream()
                .filter((a) -> a.getId().equals(id))
                .collect(Collectors.toList());
        if (result.size() == 0) {
            throw new IdNotExistException(SystemMessages.ID_NOT_EXIST_EX.getMessage());
        }
        return result;
    }

    private static List<Writer> throwIdExist(List<Writer> writers, Writer writer) throws IdExistException {
        List<Writer> result = writers.stream()
                .filter((a) -> a.getId().equals(writer.getId()))
                .collect(Collectors.toList());
        if (result.size() != 0) {
            throw new IdExistException(SystemMessages.ID_ALREADY_EXIST.getMessage());
        }
        return writers;
    }
}