package ru.shanalotte.testboxes;

import ru.shanalotte.annotations.Box;
import ru.shanalotte.annotations.BoxType;

@Box(name = "singl1", type = BoxType.SINGLETON)
public class TestSingletonBox {
    public int value = 777;

    @Override
    public String toString() {
        return "TestSingletonBox{" +
                "value=" + value +
                '}';
    }
}
