package ru.shanalotte.container;

/**
 * Супер класс для всех классов исключений во фреймворке
 *
 * @version 1.1
 * @since 1.1
 */
public class MyDIException extends RuntimeException{

    String message;

    public MyDIException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
