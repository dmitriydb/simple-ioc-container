package ru.shanalotte.domain;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.shanalotte.annotations.BoxType;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitParamsRunner.class)
public class BoxTest {

    private static final Object[] getParams(){
        return new Object[]{
            new Object[] {BoxType.SINGLETON, Date.class, "sql date", true},
            new Object[] {BoxType.PROTOTYPE, Integer.class, "Integer", false}
        };
    }

    @Test
    @Parameters(method = "getParams")
    public void parametersAreSettingCorrectly(BoxType type, Class clazz, String name, boolean isPrimary){
        Box box = new Box();
        box.setPrimary(isPrimary);
        box.setName(name);
        box.setClazz(clazz);
        box.setType(type);
        assertEquals(box.getClazz(), clazz);
        assertEquals(box.getName(), name);
        assertEquals(box.getType(), type);
        assertEquals(box.isPrimary(), isPrimary);
    }

    @Test
    public void boxTypeIsSingletonByDefault(){
        Box box = new Box();
        assertEquals(box.getType(), BoxType.SINGLETON);
    }
}