package org.AleksLis.CrudApp.view;

import org.AleksLis.CrudApp.systemMessages.SystemMessages;

import java.util.Scanner;

public class MainView {

    private boolean STATUS_MAIN_VIEW;
    private final WriterView writerView;
    private final PostView postView;
    private final LabelView labelView;
    public static final String INPUT_CHOOSE = "Input you're choose: ";
    public static final String MENU = "Actions on:\n" +
            "1. Writers \n" +
            "2. Posts \n" +
            "3. Labels \n" +
            "4. Exit";

    public MainView() {
        this.writerView = new WriterView();
        this.postView = new PostView();
        this.labelView = new LabelView();
        this.STATUS_MAIN_VIEW = true;
    }

    public boolean isSTATUS_MAIN_VIEW() {
        return STATUS_MAIN_VIEW;
    }

    public void setSTATUS_CRUD() {
        this.STATUS_MAIN_VIEW = false;
    }

    private int startAction(){
        System.out.println(MENU);
        System.out.println(INPUT_CHOOSE);
        return new Scanner(System.in).nextInt();
    }
    public void actionsOn() {
        try {
            int choose = startAction();
            while (STATUS_MAIN_VIEW) {
                switch (choose) {
                    case 1:
                        writerView.actionsOnWriters();
                        choose = startAction();
                        break;
                    case 2:
                        postView.actionsOnPosts();
                        choose = startAction();
                        break;
                    case 3:
                        labelView.actionsOnLabels();
                        choose = startAction();
                        break;
                    case 4:
                        STATUS_MAIN_VIEW = false;
                        break;
                    default:
                        System.out.println(SystemMessages.OPERATION_FAILED);
                }
            }
        } catch (Exception e) {
            System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }
}
