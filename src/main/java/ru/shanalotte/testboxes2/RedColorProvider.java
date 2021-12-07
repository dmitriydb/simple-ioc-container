package ru.shanalotte.testboxes2;

import ru.shanalotte.annotations.Box;
import ru.shanalotte.annotations.BoxType;
import ru.shanalotte.annotations.Primary;

@Primary
@Box(name="dull", type = BoxType.PROTOTYPE)
public class RedColorProvider extends ColorProvider{
    private String color = "red";
    @Override
    public String getColor() {
        return color;
    }
}
