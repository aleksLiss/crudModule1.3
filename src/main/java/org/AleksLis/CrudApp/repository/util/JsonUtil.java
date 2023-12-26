package org.AleksLis.CrudApp.repository.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.AleksLis.CrudApp.exceptions.IdExistException;
import org.AleksLis.CrudApp.exceptions.NotExistJsonException;
import org.AleksLis.CrudApp.messages.SystemMessages;
import org.AleksLis.CrudApp.model.Post;
import org.AleksLis.CrudApp.repository.WriterRepository;

import javax.swing.table.TableRowSorter;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.AleksLis.CrudApp.model.Writer;

public class JsonUtil {

    public static final String PATH = "/home/aleksei/javaProjects/CrudModule1.3/src/main/resources/";
    public static final String WRITERDB = "writers.json";
    public static final String POSTDB = "posts.json";
    public static final String LABELDB = "labels.json";



    public static Writer saveWriterToJson(Writer writer) {
        String pathFile = PATH + WRITERDB;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pathFile))){
            String res = "";
            String curr;
            while((curr = bufferedReader.readLine()) != null){
                res += curr;
            }
            Type type = new TypeToken<List<Writer>>() {
            }.getType();
            List<Writer> listWriters;
            if(res.length() == 0){
                listWriters = new ArrayList<>();
            }else {
                listWriters = new Gson().fromJson(res, type);
            }

            try {
                isIdExist(listWriters, writer.getId());
                listWriters.add(writer);
                try (FileWriter fileWriter = new FileWriter(pathFile)){
                    String listWritersToJson = new Gson().toJson(listWriters);
                    fileWriter.write(listWritersToJson);
                }catch (IOException e){
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


    public static void isIdExist(List<Writer> listWriters, Long id) throws IdExistException {
        List<Writer> result = listWriters.stream()
                .filter((writer) -> writer.getId().equals(id))
                .collect(Collectors.toList());
        if (result.size() != 0) {
            throw new IdExistException(SystemMessages.ID_ALREADY_EXIST.getMessage());
        }
    }






static class Test{
    public static void main(String[] args) {
        saveWriterToJson(new Writer(1L, "vova", "koka", new ArrayList<>()));
    }
}

























//    public static Writer getWriterFromJson(String path, Long id) throws EmptyJsonException, NotExistIdException {
//        if (getLengthOfFile(path) != 0) {
//            List<org.AleksLis.crudApp.model.Writer> listFromJson = getListWritersFromJson(path);
//            return equalsId(listFromJson, id);
//        } else {
//            throw new EmptyJsonException(Messages.EMPTY_JSON_EX.getMessage());
//        }
//    }

    /*
    public static List<org.AleksLis.crudApp.model.Writer> getListWritersFromJson(String path) throws EmptyJsonException {
        if (getLengthOfFile(path) != 0) {
            return new Gson().fromJson(readFileToString(path), getTypeForList());
        } else {
            throw new EmptyJsonException(Messages.EMPTY_JSON_EX.getMessage());
        }
    }

    public static void deleteWriterFromJson(String path, Long id) throws EmptyJsonException {
        try {
            List<org.AleksLis.crudApp.model.Writer> res = getListWritersFromJson(path);
            res.stream()
                    .peek((x) -> {
                        if (x.getId().equals(id)) {
                            x.setWriterStatus(StatusEntity.DELETED);
                        }
                    }).collect(Collectors.toList());
            writeToJson(path, res);
        } catch (EmptyJsonException e) {
            System.out.println(e.getMessage());
        }


    }

    public static void updateWriterFromJson(String path, Long id, String name, Integer age) throws EmptyJsonException {
        List<org.AleksLis.crudApp.model.Writer> getList = getListWritersFromJson(path);
        getList.stream()
                .peek((x) -> {
                    x.setName(name);
                    x.setAge(age);
                }).collect(Collectors.toList());
        writeToJson(path, getList);
    }


    private static Long getLengthOfFile(String path) {
        return new File(path).length();
    }

    private static Type getTypeForList() {
        return new TypeToken<List<org.AleksLis.crudApp.model.Writer>>() {
        }.getType();
    }

    private static String readFileToString(String path) {
        String result = "";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String res;
            while ((res = bufferedReader.readLine()) != null) {
                result += res;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private static void writeToJson(String path, List<org.AleksLis.crudApp.model.Writer> list) {
        try (FileWriter fileWriter = new FileWriter(path)) {
            String resultList = new Gson().toJson(list);
            fileWriter.write(resultList);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static org.AleksLis.crudApp.model.Writer equalsId(List<org.AleksLis.crudApp.model.Writer> listWriters, Long id) throws NotExistIdException {
        org.AleksLis.crudApp.model.Writer writerEqualId = null;

        for (Writer writer : listWriters) {
            Predicate<Long> predicate = (a) -> a.equals(id);
            if (predicate.test(writer.getId())) {
                writerEqualId = writer;
                break;
            }
        }
        if (writerEqualId != null) {
            return writerEqualId;
        } else {
            throw new NotExistIdException(Messages.NOT_EXIST_ID.getMessage());
        }
    }
    */
}