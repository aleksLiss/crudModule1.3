package org.AleksLis.CrudApp.view;

import org.AleksLis.CrudApp.controller.WriterController;
import org.AleksLis.CrudApp.model.Writer;
import org.AleksLis.CrudApp.systemMessages.SystemMessages;

import java.util.Scanner;

public class WriterView {

    private WriterController writerController;
    private Scanner scanner;

    public static final String ID = "Input id: ";
    public static final String FIRST_NAME = "Input first name: ";
    public static final String LAST_NAME = "Input last name: ";
    public static final String MENU = "Actions on writers:\n" +
            "1. Create new writer\n" +
            "2. Get writer by id\n" +
            "3. Get all writers\n" +
            "4. Update writer by id\n" +
            "5. Delete writer by id\n" +
            "6. Exit\n";




    public void saveWriter(){
        try {
            System.out.println(ID);
            Long id = (long) new Scanner(System.in).nextInt();
            System.out.println(FIRST_NAME);
            String firstName = new Scanner(System.in).next();
            System.out.println(LAST_NAME);
            String lastName = new Scanner(System.in).next();
            writerController.saveWriter(new Writer(id, firstName, lastName));
            System.out.println(SystemMessages.OPERATION_SUCCESS.getMessage());
        }catch (Exception e){
            System.out.println(SystemMessages.OPERATION_FAILED.getMessage());
        }



    }

}





class Test{
    public static void main(String[] args) {
        WriterView v = new WriterView();
        v.saveWriter();
    }
}