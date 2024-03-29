package org.AleksLis.CrudApp.view;

import org.AleksLis.CrudApp.controller.LabelController;
import org.AleksLis.CrudApp.model.Label;
import org.AleksLis.CrudApp.systemMessages.SystemMessages;

import java.util.List;
import java.util.Scanner;

public class LabelView {

    private final LabelController labelController;
    private boolean statusLabelView;

    public LabelView() {
        this.labelController = new LabelController();
        this.statusLabelView = true;
    }

    private int startAction(){
        System.out.println(SystemMessages.MENU_LABEL_VIEW);
        System.out.println(SystemMessages.INPUT_CHOOSE);
        return new Scanner(System.in).nextInt();
    }

    public void actionsOnLabels() {
        try {
            int choose = startAction();
            while (statusLabelView) {
                switch (choose) {
                    case 1:
                        saveLabel();
                        choose = startAction();
                        break;
                    case 2:
                        getLabel();
                        choose = startAction();
                        break;
                    case 3:
                        getAllLabels();
                        choose = startAction();
                        break;
                    case 4:
                        updateLabel();
                        choose = startAction();
                        break;
                    case 5:
                        deleteLabel();
                        choose = startAction();
                        break;
                    case 6:
                        statusLabelView = false;
                        break;
                    default:
                        System.out.println(SystemMessages.OPERATION_FAILED);
                }
            }
        } catch (Exception e) {
            System.out.println(SystemMessages.OPERATION_FAILED);
        }

    }

    public void getLabel() {
        try {
            System.out.println(SystemMessages.INPUT_ID);
            long id = new Scanner(System.in).nextInt();
            Label label = labelController.getById(id);
            System.out.println("Id: " + label.getId());
            System.out.println("Name: " + label.getName());
            System.out.println("Status: " + label.getLabelStatus());
        } catch (Exception e) {
            System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }

    ;

    public void getAllLabels() {
        try {
            List<Label> labelList = labelController.getAll();
            for (Label label : labelList) {
                System.out.println("Id: " + label.getId());
                System.out.println("Name: " + label.getName());
                System.out.println("Status: " + label.getLabelStatus());
            }
        } catch (Exception e) {
            System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }

    ;

    public void saveLabel() {
        try {
            System.out.println(SystemMessages.INPUT_ID);
            long id = new Scanner(System.in).nextInt();
            System.out.println(SystemMessages.INPUT_NAME);
            String name = new Scanner(System.in).next();
            labelController.saveLabel(new Label(id, name));
        } catch (Exception e) {
            System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }

    ;

    public void updateLabel() {
        try {
            System.out.println(SystemMessages.INPUT_ID);
            long id = new Scanner(System.in).nextInt();
            Label label = labelController.getById(id);
            System.out.println(SystemMessages.INPUT_NAME);
            String name = new Scanner(System.in).next();
            label.setName(name);
            labelController.updateLabel(label);
        } catch (Exception e) {
            System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }

    ;

    public void deleteLabel() {
        try {
            System.out.println(SystemMessages.INPUT_ID);
            long id = new Scanner(System.in).nextInt();
            labelController.deleteById(id);
        } catch (Exception e) {
            System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }


}
