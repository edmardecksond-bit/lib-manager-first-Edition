package com.nb.library.exception;

public class BorrowLimitExceededException extends RuntimeException {

    public BorrowLimitExceededException(String memberName, int limit) {
        super(memberName + " a atteint la limite de " + limit + " emprunts");
    }
}
