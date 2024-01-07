package org.AleksLis.CrudApp.view;

import org.AleksLis.CrudApp.controller.WriterController;
import org.AleksLis.CrudApp.model.Writer;
import org.AleksLis.CrudApp.systemMessages.SystemMessages;

import java.util.List;
import java.util.Scanner;

public class WriterView {

    private WriterController writerController;
    public static final String INPUT_ID = "Input id: ";
    public static final String INPUT_FIRST_NAME = "Input first name: ";
    public static final String INPUT_LAST_NAME = "Input last name: ";

    public static final String ACTION_ON_WRITERS = "Choose action on writers: ";
    public static final String MENU = "Actions on writers:\n" +
            "1. Create new writer\n" +
            "2. Get writer by id\n" +
            "3. Get all writers\n" +
            "4. Update writer by id\n" +
            "5. Delete writer by id\n" +
            "6. Exit\n";

    public void actionsOnWriters(){
        System.out.println(ACTION_ON_WRITERS);
        int choose = new Scanner(System.in).nextInt();
        switch (choose){
            case 1:
                saveWriter();
                break;
            case 2:
                getWriter();
                break;
            case 3:
                getAllWriters();
                break;
            case 4:
                updateWriter();
                break;
            case 5:
                deleteWriter();
                break;
            case 6:
                break;
            default:
                System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }


    public void saveWriter(){
        try {
            System.out.println(INPUT_ID);
            long id = new Scanner(System.in).nextInt();
            System.out.println(INPUT_FIRST_NAME);
            String firstName = new Scanner(System.in).next();
            System.out.println(INPUT_LAST_NAME);
            String lastName = new Scanner(System.in).next();
            writerController.saveWriter(new Writer(id, firstName, lastName));
            System.out.println(SystemMessages.OPERATION_SUCCESS.getMessage());
        }catch (Exception e){
            System.out.println(SystemMessages.OPERATION_FAILED.getMessage());
        }
    }

    public void getWriter(){
        try {
            System.out.println(INPUT_ID);
            Long id = (long) new Scanner(System.in).nextInt();
            Writer writerFromDB = writerController.getById(id);
            System.out.println("Id: " + writerFromDB.getId());
            System.out.println("First name: " + writerFromDB.getFirstName());
            System.out.println("Last name: " + writerFromDB.getLastName());
        }catch (Exception e){
            System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }

    public void getAllWriters(){
        try {
            List<Writer> writerList = writerController.getAll();
            for(Writer writer: writerList){
                System.out.println("Id: " + writer.getId());
                System.out.println("First name: " + writer.getFirstName());
                System.out.println("Last name " + writer.getLastName());
            }
        }catch (Exception e){
            System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }

    public void updateWriter(){
        try {

            System.out.println(INPUT_ID);
            long id = new Scanner(System.in).nextInt();
            Writer writer = writerController.getById(id);

            System.out.println(INPUT_FIRST_NAME);
            String firstName = new Scanner(System.in).next();
            System.out.println(INPUT_LAST_NAME);
            String lastName = new Scanner(System.in).next();

            writer.setFirstName(firstName);
            writer.setLastName(lastName);

            writerController.updateWriter(writer);
            System.out.println(SystemMessages.OPERATION_SUCCESS);
        }catch (Exception e){
            System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }

    public void deleteWriter(){
        try {
            System.out.println(INPUT_ID);
            long id = new Scanner(System.in).nextInt();
            writerController.deleteWriter(id);
            System.out.println(SystemMessages.OPERATION_SUCCESS);
        }catch (Exception e){
            System.out.println(SystemMessages.OPERATION_FAILED);
        }
    }
}
