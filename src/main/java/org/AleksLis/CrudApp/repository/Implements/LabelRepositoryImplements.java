package org.AleksLis.CrudApp.repository.Implements;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.AleksLis.CrudApp.exceptions.EmptyDBException;
import org.AleksLis.CrudApp.exceptions.IdExistException;
import org.AleksLis.CrudApp.exceptions.IdNotExistException;
import org.AleksLis.CrudApp.model.Label;
import org.AleksLis.CrudApp.model.StatusEntity;
import org.AleksLis.CrudApp.model.Writer;
import org.AleksLis.CrudApp.repository.LabelRepository;
import org.AleksLis.CrudApp.repository.util.Util;
import org.AleksLis.CrudApp.systemMessages.SystemMessages;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class LabelRepositoryImplements implements LabelRepository {


    @Override
    public Label getById(Long id) {
        Label labelFromDB = null;
        try {
            List<Label> listLabels = getListLabelsFromJsonString(getStringFromJson());
            Util.emptyDb(listLabels.size());
            try {
                List<Label> result = filterListLabelsById(id, listLabels);
                Util.idNotExist(result.size());
                labelFromDB = result.get(0);
            } catch (IdNotExistException ignored) {
            }
        } catch (EmptyDBException ignored) {
        }
        return labelFromDB;

    }

    @Override
    public List getAll() {
        List<Label> labelsFromDB = null;
        try {
            labelsFromDB = getListLabelsFromJsonString(getStringFromJson());
            Util.emptyDb(labelsFromDB.size());
        } catch (EmptyDBException ignored) {
        }
        return labelsFromDB;

    }

    @Override
    public Label save(Label label) {
        String pathFile = getPathFile();
        List<Label> listLabels = getListLabelsFromJsonString(getStringFromJson());
        try {
            List<Label> result = filterListLabelsById(label.getId(), listLabels);
            Util.idExist(result.size());
            result.add(label);
            writeListOfLabelsToDB(pathFile, result);
        } catch (IdExistException ignored) {
        }
        return label;

    }

    @Override
    public Label update(Label label) {
        Label labelFromDB = null;
        String pathFile = getPathFile();
        try {
            List<Label> listLabels = getListLabelsFromJsonString(getStringFromJson());
            Util.emptyDb(listLabels.size());
            try {
                List<Label> result = filterListLabelsById(label.getId(), listLabels);
                Util.idNotExist(result.size());
                labelFromDB = updateLabel(result.get(0), label);
                result.add(labelFromDB);
                writeListOfLabelsToDB(pathFile, result);
            } catch (IdNotExistException ignored) {
            }
        } catch (EmptyDBException ignored) {
        }
        return labelFromDB;

    }

    @Override
    public void delete(Long id) {

        String pathFile = getPathFile();
        try {
            List<Label> listLabels = getListLabelsFromJsonString(getStringFromJson());
            Util.emptyDb(listLabels.size());
            try {
                List<Label> result = filterListLabelsById(id, listLabels);
                Util.idNotExist(result.size());
                Label labelFromDB = result.get(0);
                labelFromDB.setLabelStatus(StatusEntity.DELETE);
                result.add(labelFromDB);
                writeListOfLabelsToDB(pathFile, result);
            } catch (IdNotExistException ignored) {
            }
        } catch (EmptyDBException ignored) {
        }


    }

    private List<Label> filterListLabelsById(Long id, List<Label> labels) {
        return labels.stream()
                .filter((lb) -> lb.getId().equals(id))
                .collect(Collectors.toList());
    }

    private List<Label> getListLabelsFromJsonString(String fromJson) {
        Type type = new TypeToken<List<Label>>() {
        }.getType();
        return new Gson().fromJson(fromJson, type);
    }

    private Label updateLabel(Label labelFromDB, Label updateLabel) {
        labelFromDB.setName(updateLabel.getName());
        labelFromDB.setLabelStatus(updateLabel.getLabelStatus());
        return updateLabel;
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

}
