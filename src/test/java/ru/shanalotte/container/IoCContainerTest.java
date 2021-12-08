package ru.shanalotte.container;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import ru.shanalotte.annotations.*;

import static org.junit.jupiter.api.Assertions.*;

public class IoCContainerTest {

    public static class F{};

    @Box
    public static class Outer4{
        @MyInject
        F f;
    }

    @Component(name = "myComp")
    public static class E{

    };

    public abstract static class InnerA{
        public abstract String value();
    }

    @Component(name = "inner")
    public static class Inner extends InnerA{
        public String value(){
            return "xxx";
        }
    }

    @Box
    public static class Outer3{
        @MyInject
        @ChooseName(name = "idontexist")
        E e;
    }

    @Component(name = "inner2")
    public static class Inner2 extends InnerA{
        public String value(){
            return "xxx2";
        }
    }

    @Box(name = "outer")
    public static class Outer{
        @MyInject
        @ChooseName(name = "inner")
        public InnerA inner;
    }

    @Box(name = "outer2")
    public static class Outer2{
        @MyInject
        @ChooseName(name = "inner2")
        public InnerA inner;
    }

    @Box(name = "A")
    public static class A{};

    @Box(name = "B")
    public static class B extends A{};

    @Box(name = "C")
    public static class C extends A{};

    @Primary
    @Box(name = "D")
    public static class D extends A{};

    @Component
    public static class I{
        @MyInject
        A a;
    }

    @Component
    public static class I2{
        @MyInject
        @ChooseName(name = "C")
        A a;
    }

    @Box(
        name = "prototype",
        type = BoxType.PROTOTYPE
    )
    public static class ProtoBox{
        private String getValue(){
            return "value";
        }
    };

    @Box(
            name = "singleton",
            type = BoxType.SINGLETON
    )
    public static class SingleBox{

        private String getValue(){
            return "value";
        }

    };

    @Test
    public void boxIsLoaded(){
        IoCContainer.loadBoxes();
        ProtoBox testBox = (ProtoBox) IoCContainer.getBox(ProtoBox.class);
        assertEquals(testBox.getValue(),"value");
    }

    @Test
    public void prototypeBoxesAreNotTheSame(){
        IoCContainer.loadBoxes();
        ProtoBox testBox = (ProtoBox) IoCContainer.getBox(ProtoBox.class);
        ProtoBox testBox2 = (ProtoBox) IoCContainer.getBox(ProtoBox.class);
        assertNotSame(testBox2, testBox);
    }

    @Test
    public void singletonBoxesAreTheSame(){
        IoCContainer.loadBoxes();
        SingleBox testBox = (SingleBox)IoCContainer.getBox(SingleBox.class);
        SingleBox testBox2 = (SingleBox)IoCContainer.getBox(SingleBox.class);
        assertSame(testBox, testBox2);
    }

    @Test
    public void loadingByNameIsWorking(){
        IoCContainer.loadBoxes();
        SingleBox testBox = (SingleBox)IoCContainer.getBox("singleton");
        String result = testBox.getValue();
        assertEquals(result, "value");
    }

    @Test
    public void singletonIsDefaultType(){
        IoCContainer.loadBoxes();
        C c1 = (C)IoCContainer.getBox(C.class);
        C c2 = (C)IoCContainer.getBox(C.class);
        assertSame(c1, c2);
    }

    @Test
    public void primaryIsWorking(){
        IoCContainer.loadBoxes();
        I i = (I)IoCContainer.getBox(I.class);
        assertTrue(i.a instanceof D);
    }

    @Test
    public void injectingByNameIsWorking(){
        IoCContainer.loadBoxes();
        I2 i = (I2)IoCContainer.getBox(I2.class);
        assertTrue(i.a instanceof C);
    }

    @Test
    public void componentsByNameIsWorking(){
        IoCContainer.loadBoxes();
        E e = (E)IoCContainer.getBox("myComp");
        assertTrue(e instanceof E);
    }

    @Test(expected = NoSuchBoxException.class)
    public void exceptionThrownWhenNoSuchBoxNameLoaded(){
        IoCContainer.loadBoxes();
        Object x = IoCContainer.getBox("I dont exist");

    }

    @Test(expected = NoSuchBoxException.class)
    public void exceptionThrownWhenNoSuchBoxClassLoaded(){
        IoCContainer.loadBoxes();
        Object x = IoCContainer.getBox(java.lang.Exception.class);

    }

    @Test
    public void componentInjectingIsWorking(){
        IoCContainer.loadBoxes();
        Outer o = (Outer)IoCContainer.getBox("outer");
        assertTrue(o.inner.value().equals("xxx"));
    }

    @Test
    public void componentInjectingByNameIsWorking(){
        IoCContainer.loadBoxes();
        Outer2 o = (Outer2)IoCContainer.getBox("outer2");
        assertTrue(o.inner.value().equals("xxx2"));
    }

    @Test(expected = NoSuchBoxException.class)
    public void injectingNonexistantBoxThrowsError(){
        IoCContainer.loadBoxes();
        Outer3 o = (Outer3)IoCContainer.getBox(Outer3.class);
    }

    @Test(expected = NoSuchBoxException.class)
    public void injectingNonexistantBoxByClassThrowsError(){
        IoCContainer.loadBoxes();
        Outer4 o = (Outer4)IoCContainer.getBox(Outer4.class);
    }
}