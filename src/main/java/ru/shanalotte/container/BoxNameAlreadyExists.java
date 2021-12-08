package ru.shanalotte.container;

/**
 * Исключение, которое выбрасывается в случае, если пользователь пытается зарегистрировать 2 Box-а с одним и тем же именем
 *
 * @since 1.1
 * @version 1.0
 */
public class BoxNameAlreadyExists extends MyDIException{
    public BoxNameAlreadyExists(String message) {
        super(message);
    }
}
