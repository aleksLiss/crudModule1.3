package org.AleksLis.CrudApp.view;

import org.AleksLis.CrudApp.systemMessages.SystemMessages;

import java.util.Scanner;

public class MainView {

    WriterView writerView;
    PostView postView;
    LabelView labelView;
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
    }

    public void actionsOn(){
        try {
            System.out.println(MENU);
            System.out.println(INPUT_CHOOSE);
            int choose = new Scanner(System.in).nextInt();
            switch (choose){
                case 1:
                    writerView.actionsOnWriters();
                    break;
                case 2:
                    postView.actionsOnPosts();
                    break;
                case 3:
                    labelView.actionsOnLabels();
                    break;
                case 4:
                    break;
                default:
                    System.out.println(SystemMessages.OPERATION_FAILED);
            }
        }catch (Exception e){
            System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }
}