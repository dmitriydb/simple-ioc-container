package ru.shanalotte.domain;

import ru.shanalotte.annotations.BoxType;

/**
 * Класс, реализующий логику Bean из Spring-а
 * Внедряется в другие объекты с помощью аннотации @MyInject
 *
 * @version 1.0
 * @since 1.0
 */
public class Box {
    /**
     * Тип внедряемого компонента (singleton, prototype)
     */
    private BoxType type;
    /**
     * Класс внедряемого объекта
     */
    private Class clazz;
    /**
     * Пользовательское имя компонента
     */
    private String name;
    /**
     * Если Box отмечен как @Primary, то устанавливается в true
     */
    private boolean isPrimary;

    public Box() {

    }

    public BoxType getType() {
        return type;
    }

    public void setType(BoxType type) {
        this.type = type;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    @Override
    public String toString() {
        return "Box{" +
                "type=" + type +
                ", clazz=" + clazz +
                ", name='" + name + '\'' +
                ", isPrimary=" + isPrimary +
                '}';
    }
}
