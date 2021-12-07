package ru.shanalotte.annotations;

/**
 * Тип внедряемых объектов
 * Singleton - аналог singleton bean из Spring, на каждое объявление Box создается только один объект и возвращается по запросу
 * Prototype - аналог prototype bean из Spring, при каждом запросе создается новый объект
 *
 * @version 1.0
 * @since 1.0
 */
public enum BoxType {
    SINGLETON, PROTOTYPE
}
