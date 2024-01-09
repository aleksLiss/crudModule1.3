package org.AleksLis.CrudApp.repository.util;

import java.io.*;

public class Util{

    public static final String PATH = "/CrudModule1.3/src/main/resources/";
    public static final String WRITERDB = "writers.json";
    public static final String POSTDB = "posts.json";
    public static final String LABELDB = "labels.json";

    public static String jsonToString(BufferedReader bufferedReader){
        StringBuilder StringFromJson = new StringBuilder();
        try {
            String current;
            while((current = bufferedReader.readLine()) != null){
                StringFromJson.append(current);
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return new String(StringFromJson);
    }
};
