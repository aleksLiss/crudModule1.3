package org.AleksLis.CrudApp.repository.util;

import com.google.gson.Gson;
import org.AleksLis.CrudApp.exceptions.EmptyDBException;
import org.AleksLis.CrudApp.exceptions.IdExistException;
import org.AleksLis.CrudApp.exceptions.IdNotExistException;
import org.AleksLis.CrudApp.model.Post;
import org.AleksLis.CrudApp.systemMessages.SystemMessages;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.AleksLis.CrudApp.model.Writer;

public class Util{

    public static final String PATH = "/home/aleksei/javaProjects/CrudModule1.3/src/main/resources/";
    public static final String WRITERDB = "writers.json";
    public static final String POSTDB = "posts.json";
    public static final String LABELDB = "labels.json";

    public static String jsonToString(BufferedReader bufferedReader){
        String result= null;
        try {
            String current;
            while((current = bufferedReader.readLine()) != null){
                result += current;
            }
        }catch (IOException e){
            System.out.println(e);
        }
        return result;
    }

    public static void emptyDb(Integer length) throws EmptyDBException{
        if(length == 0){
            throw new EmptyDBException(SystemMessages.EMPTY_DB_EX.getMessage());
        }
    }

    public static void idNotExist(Integer length) throws IdNotExistException{
        if(length == 0){
            throw new IdNotExistException(SystemMessages.ID_NOT_EXIST_EX.getMessage());
        }
    }




    public Util() {
    }





    public static Writer getWriterById(List<Writer> listWriters, Long id) throws IdNotExistException {
        List<Writer> result = listWriters.stream()
                .filter((writer) -> writer.getId().equals(id))
                .collect(Collectors.toList());
        if (result.size() != 0) {
            return result.get(0);
        } else {
            throw new idNotExistException(SystemMessages.ID_NOT_EXIST_EX.getMessage());
        }
    }


    public static void isIdExist(List<Writer> listWriters, Long id) throws IdExistException {
        List<Writer> result = listWriters.stream()
                .filter((writer) -> writer.getId().equals(id))
                .collect(Collectors.toList());
        if (result.size() != 0) {
            throw new IdExistException(SystemMessages.ID_ALREADY_EXIST.getMessage());
        }
    }

    public static List<Writer> getListWritersFromJson(String jsonToString, Type type) {
        List<Writer> listWriters;
        if (jsonToString.length() == 0) {
            listWriters = new ArrayList<>();
        } else {
            listWriters = new Gson().fromJson(jsonToString, type);
        }
        return listWriters;
    }

    public static List<Post> getListPostsFromJson(String jsonToString, Type type) {
        List<Post> listPosts;
        if (jsonToString.length() == 0) {
            listPosts = new ArrayList<>();
        } else {
            listPosts = new Gson().fromJson(jsonToString, type);
        }
        return listPosts;
    }

    public static <T> getEntity(List <T>){
        try {

        }catch ()
    }

};


