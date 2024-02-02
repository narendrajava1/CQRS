package org.example.exceptions;

public class PreconditionFailed extends Exception {
    public PreconditionFailed(String msg) {
        super(msg);
    }
}