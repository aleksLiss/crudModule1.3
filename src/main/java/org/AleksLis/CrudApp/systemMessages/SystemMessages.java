package org.AleksLis.CrudApp.systemMessages;

public enum SystemMessages {
    ID_ALREADY_EXIST("Entity with this id already exist"),
    ID_NOT_EXIST_EX("Entity with this id not exist"),
    WRITE_TO_JSON_EX("Write to json has not completed"),
    EMPTY_DB_EX("Empty database"),
    OPERATION_SUCCESS("Operation successes"),
    OPERATION_FAILED("Operation failed"),
    INPUT_CHOOSE("Chose number"),
    INPUT_NAME("Input name: "),
    MENU_LABEL_VIEW("1. Create new label\n" +
            "2. Get label by id\n" +
            "3. Get all labels" +
            "4. Update label by id\n" +
            "5. Delete label by id\n" +
            "6. Exit\n"),
    INPUT_CONTENT("Input content: "),
    MENU_POST_VIEW("1. Create new post\n" +
            "2. Get post by id\n" +
            "3. Get all posts\n" +
            "4. Update post by id\n" +
            "5. Delete post by id\n" +
            "6. Exit\n"),
    INPUT_ID("Input id: "),
    INPUT_FIRST_NAME("Input first name: "),
    INPUT_LAST_NAME("Input last name: "),
    MENU_WRITER_VIEW("Actions on writers:\n" +
            "1. Create new writer\n" +
            "2. Get writer by id\n" +
            "3. Get all writers\n" +
            "4. Update writer by id\n" +
            "5. Delete writer by id\n" +
            "6. Exit\n"),
    MAIN_MENU("Actions on:\n" +
            "1. Writers \n" +
            "2. Posts \n" +
            "3. Labels \n" +
            "4. Exit"),

    ;
    private final String message;
     SystemMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
