package ru.shanalotte.testboxes;

import ru.shanalotte.annotations.Box;
import ru.shanalotte.annotations.BoxType;

import java.util.Random;

@Box(name = "proto", type = BoxType.PROTOTYPE)
public class TestPrototypeBox {
    public int value = new Random().nextInt(100000);

    @Override
    public String toString() {
        return "TestPrototypeBox{" +
                "value=" + value +
                '}';
    }
}
