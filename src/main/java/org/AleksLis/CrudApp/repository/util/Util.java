package org.AleksLis.CrudApp.repository.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.AleksLis.CrudApp.exceptions.IdExistException;
import org.AleksLis.CrudApp.exceptions.IdNotExistException;
import org.AleksLis.CrudApp.systemMessages.SystemMessages;
import org.AleksLis.CrudApp.model.StatusEntity;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.AleksLis.CrudApp.model.Writer;

public class Util {

    public static final String PATH = "/home/aleksei/javaProjects/CrudModule1.3/src/main/resources/";
    public static final String WRITERDB = "writers.json";
    public static final String POSTDB = "posts.json";
    public static final String LABELDB = "labels.json";

    public Util() {
    }
    public static Writer getWriterById(List<Writer> listWriters, Long id) throws IdNotExistException {
        Writer getWriter;
        List<Writer> result = listWriters.stream()
                .filter((writer) -> writer.getId().equals(id))
                .collect(Collectors.toList());
        if (result.size() != 0) {
            getWriter = result.get(0);
        } else {
            throw new IdNotExistException(SystemMessages.ID_NOT_EXIST_EX.getMessage());
        }
        return getWriter;
    }

    public static Type getTypeOfListWriters() {
        return new TypeToken<List<Writer>>() {
        }.getType();
    }

    public static String JsonToString(BufferedReader bufferedReader) {
        String result = "";
        String current;
        try {
            while ((current = bufferedReader.readLine()) != null) {
                result += current;
            }
        } catch (IOException ignored) {
        }
        return result;
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

};


