package com.kodo.codewars;

public enum RegisterResult {

    USER_NOT_FOUND("Specified user was not found on CodeWars. please ensure the user name is cAse SeNsItIvE"),
    USER_ALREADY_REGISTERED("Specified user has already been registered to the system"),
    USER_REGISTERED("Specified user has been registered to the system"),
    USER_NOT_IN_CLAN("Specified user is not in the codewars clan"),
    SERVER_ERROR("Invalid response from the CodeWars server, please try again later");

    private String error_message;

    private RegisterResult(String message){
        this.error_message = message;
    }

    public String getErrorMessage(){
        return error_message;
    }
}
