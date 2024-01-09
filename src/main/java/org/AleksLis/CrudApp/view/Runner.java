package org.AleksLis.CrudApp.view;

import org.AleksLis.CrudApp.systemMessages.SystemMessages;

import java.util.Scanner;

public class Runner {
    private boolean statusMainView;
    private final WriterView writerView;
    private final PostView postView;
    private final LabelView labelView;
    public Runner() {
        this.writerView = new WriterView();
        this.postView = new PostView();
        this.labelView = new LabelView();
        this.statusMainView = true;
    }
    public void setStatusMainView() {
        this.statusMainView = false;
    }
    private int startAction(){
        System.out.println(SystemMessages.MAIN_MENU);
        System.out.println(SystemMessages.INPUT_CHOOSE);
        return new Scanner(System.in).nextInt();
    }
    public void run() {
        try {
            int choose = startAction();
            while (statusMainView) {
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
                        setStatusMainView();
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
