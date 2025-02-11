package org.davidescobarr.quizbackend.enums;

public enum TypeQuestionEnum {
    ONE_VARIANT,
    MANY_VARIANTS;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
