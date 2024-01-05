package org.AleksLis.CrudApp.systemMessages;

public enum SystemMessages {

    JSON_NOT_EXIST_EX("File ****.json is not exist"),

    ID_ALREADY_EXIST("Entity with this id already exist"),
    ID_NOT_EXIST_EX("Entity with this id not exist"),

    WRITE_TO_JSON_EX("Write to json has not completed"),

    EMPTY_DB_EX("Empty database"),

    OPERATION_SUCCESS("Operation successes"),

    OPERATION_FAILED("Operation failed")

    ;
    private String message;
     SystemMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
