package ru.shanalotte.testboxes2;

import ru.shanalotte.annotations.Box;
import ru.shanalotte.annotations.BoxType;
import ru.shanalotte.annotations.Primary;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Box(name="monochrome")
public class MonochromeColorProvider extends ColorProvider{
    @Override
    public String getColor() {
        return ThreadLocalRandom.current().nextInt(10) < 5 ? "black" : "white";
    }
}
