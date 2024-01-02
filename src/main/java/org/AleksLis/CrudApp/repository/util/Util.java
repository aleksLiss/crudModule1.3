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

    public static void idExist(Integer length) throws IdExistException{
        if(length != 0){
            throw new IdExistException(SystemMessages.ID_ALREADY_EXIST.getMessage());
        }
    }
};


