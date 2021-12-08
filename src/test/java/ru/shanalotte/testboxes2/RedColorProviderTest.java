package ru.shanalotte.testboxes2;


import junitparams.JUnitParamsRunner;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitParamsRunner.class)
class RedColorProviderTest {

    @Test
    public void returnsAlwaysRedColor(){
        String color = new RedColorProvider().getColor();
        assertEquals(color, "red");
    }

}