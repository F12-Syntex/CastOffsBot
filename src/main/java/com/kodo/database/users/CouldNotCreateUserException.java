package com.kodo.database.users;

public class CouldNotCreateUserException extends RuntimeException{
        private static final long serialVersionUID = 1L;
        public CouldNotCreateUserException(String message) {
            super(message);
        }
}
