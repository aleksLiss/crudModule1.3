package org.AleksLis.CrudApp.messages;

public enum SystemMessages {

    JSON_NOT_EXIST_EX("File ****.json is not exist"),

    ID_ALREADY_EXIST("Entity with this id already exist"),

    WRITE_TO_JSON_EX("Write to json has not completed")

    ;
    private String message;
     SystemMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
