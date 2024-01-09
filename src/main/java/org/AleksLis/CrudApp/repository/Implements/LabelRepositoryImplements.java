package org.AleksLis.CrudApp.repository.Implements;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.AleksLis.CrudApp.exceptions.EmptyDBException;
import org.AleksLis.CrudApp.exceptions.IdExistException;
import org.AleksLis.CrudApp.exceptions.IdNotExistException;
import org.AleksLis.CrudApp.model.Label;
import org.AleksLis.CrudApp.model.StatusEntity;
import org.AleksLis.CrudApp.repository.LabelRepository;
import org.AleksLis.CrudApp.repository.util.Util;
import org.AleksLis.CrudApp.systemMessages.SystemMessages;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LabelRepositoryImplements implements LabelRepository {


    @Override
    public Label getById(Long id) {
        Label label = null;
        try {
            List<Label> labelList = getListLabelsFromDB(getStringFromJson());
            throwEmptyDB(labelList);
            try {
                List<Label> labels = throwIdNotExist(labelList, id);
                label = labels.get(0);
            } catch (IdNotExistException ex) {
                System.out.println(SystemMessages.OPERATION_FAILED);
                System.out.println(ex.getMessage());
            }
        } catch (EmptyDBException ex) {
            System.out.println(SystemMessages.OPERATION_FAILED);
            System.out.println(ex.getMessage());
        }
        return label;

    }

    @Override
    public List<Label> getAll() {
        List<Label> labelsFromDB = null;
        try {
            labelsFromDB = getListLabelsFromDB(getStringFromJson());
            throwEmptyDB(labelsFromDB);
        } catch (EmptyDBException ex) {
            System.out.println(SystemMessages.OPERATION_FAILED);
            System.out.println(ex.getMessage());
        }
        return labelsFromDB;

    }

    @Override
    public Label save(Label label) {
        try {
            String pathFile = getPathFile();
            File file = new File(pathFile);
            if (file.length() == 0) {
                try {
                    List<Label> labelList = new ArrayList<>();
                    labelList.add(label);
                    writeListOfLabelsToDB(pathFile, labelList);
                    System.out.println(SystemMessages.OPERATION_SUCCESS);
                } catch (Exception ex) {
                    System.out.println(SystemMessages.OPERATION_FAILED);
                    System.out.println(ex.getMessage());
                }
            } else {
                try {
                    List<Label> labelList = getListLabelsFromDB(getStringFromJson());
                    List<Label> result = throwIdExist(labelList, label);
                    result.add(label);
                    writeListOfLabelsToDB(getPathFile(), result);
                    System.out.println(SystemMessages.OPERATION_SUCCESS);
                } catch (IdExistException ex) {
                    System.out.println(SystemMessages.OPERATION_FAILED);
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(SystemMessages.OPERATION_FAILED);
            System.out.println(e.getMessage());
        }
        return label;
    }

    @Override
    public Label update(Label label) {

        Label labelResult = null;
        try {
            List<Label> labelList = getListLabelsFromDB(getStringFromJson());
            throwEmptyDB(labelList);
            try {
                List<Label> filterLabelsById = throwIdNotExist(labelList, label.getId());
                filterLabelsById.remove(0);
                filterLabelsById.add(label);
                labelResult = filterLabelsById.get(0);
                writeListOfLabelsToDB(getPathFile(), filterLabelsById);
                System.out.println(SystemMessages.OPERATION_SUCCESS);
            } catch (IdNotExistException ex) {
                System.out.println(SystemMessages.OPERATION_FAILED);
                System.out.println(ex.getMessage());
            }
        } catch (EmptyDBException ex) {
            System.out.println(SystemMessages.OPERATION_FAILED);
            System.out.println(ex.getMessage());
        }
        return labelResult;

    }

    @Override
    public void delete(Long id) {
        String pathFile = getPathFile();
        try {
            List<Label> labelList = getListLabelsFromDB(getStringFromJson());
            throwEmptyDB(labelList);
            try {
                List<Label> result = throwIdNotExist(labelList, id);
                List<Label> res = result.stream()
                        .peek(label-> label.setLabelStatus(StatusEntity.DELETE))
                        .collect(Collectors.toList());
                writeListOfLabelsToDB(pathFile, res);
                System.out.println(SystemMessages.OPERATION_SUCCESS);
            } catch (IdNotExistException ex) {
                System.out.println(SystemMessages.OPERATION_FAILED);
                System.out.println(ex.getMessage());
            }
        } catch (EmptyDBException ex) {
            System.out.println(SystemMessages.OPERATION_FAILED);
            System.out.println(ex.getMessage());
        }

    }

    private List<Label> getListLabelsFromDB(String fromJson) {
        Type type = new TypeToken<List<Label>>() {
        }.getType();
        return new Gson().fromJson(fromJson, type);
    }

    private void writeListOfLabelsToDB(String pathFile, List<Label> labels) {
        try (FileWriter fileWriter = new FileWriter(pathFile)) {
            String listLabelsToJson = new Gson().toJson(labels);
            fileWriter.write(listLabelsToJson);
        } catch (IOException e) {
            System.out.println(SystemMessages.WRITE_TO_JSON_EX.getMessage());
        }
    }

    private String getPathFile() {
        return Util.PATH + Util.LABELDB;
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

    private static void throwEmptyDB(List<Label> labels) throws EmptyDBException{
        if(labels == null){
            throw new EmptyDBException(SystemMessages.EMPTY_DB_EX.getMessage());
        }
    }

    private static List<Label> throwIdNotExist(List<Label> labels, Long id) throws IdNotExistException {
        List<Label> result = labels.stream()
                .filter((a) -> a.getId().equals(id))
                .collect(Collectors.toList());
        if (result.size() == 0) {
            throw new IdNotExistException(SystemMessages.ID_NOT_EXIST_EX.getMessage());
        }
        return result;
    }

    private static List<Label> throwIdExist(List<Label> labels, Label label) throws IdExistException {
        List<Label> result = labels.stream()
                .filter((a) -> a.getId().equals(label.getId()))
                .collect(Collectors.toList());
        if (result.size() != 0) {
            throw new IdExistException(SystemMessages.ID_ALREADY_EXIST.getMessage());
        }
        return labels;
    }
}



