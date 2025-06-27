package br.com.julio.exception;

public class EmptyStorageException extends RuntimeException{
    public EmptyStorageException(String message) {
        super(message);
    }
}
