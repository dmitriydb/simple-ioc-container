package ru.shanalotte.container;

/**
 * Исключение, которое бросается, когда из пользовательского кода пытаются получить неизвестный Box
 *
 * @version 1.1
 * @since 1.0
 */
public class NoSuchBoxException extends MyDIException{

    public NoSuchBoxException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "NoSuchBoxException " + message;
    }
}
