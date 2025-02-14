package org.davidescobarr.quizbackend.util.exception;

public class PassedTestUserNotFoundException extends RuntimeException {

    public PassedTestUserNotFoundException(String exception) {
        super(exception);
    }

}
