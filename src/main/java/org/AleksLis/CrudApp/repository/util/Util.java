package org.AleksLis.CrudApp.repository.util;

import org.AleksLis.CrudApp.exceptions.IdExistException;
import org.AleksLis.CrudApp.exceptions.IdNotExistException;
import org.AleksLis.CrudApp.systemMessages.SystemMessages;

import java.io.*;

public class Util{

    public static final String PATH = "/home/aleksei/javaProjects/CrudModule1.3/src/main/resources/";
    public static final String WRITERDB = "writers.json";
    public static final String POSTDB = "posts.json";
    public static final String LABELDB = "labels.json";

    public static String jsonToString(BufferedReader bufferedReader){
        String result = "";
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

    public static void throwIdNotExist(Integer length) throws IdNotExistException{
        if(length == 0){
            throw new IdNotExistException(SystemMessages.ID_NOT_EXIST_EX.getMessage());
        }
    }

    public static void throwIdExist(Integer length) throws IdExistException{
        if(length != 0){
            throw new IdExistException(SystemMessages.ID_ALREADY_EXIST.getMessage());
        }
    }
};
