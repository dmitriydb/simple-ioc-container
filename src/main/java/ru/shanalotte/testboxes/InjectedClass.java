package ru.shanalotte.testboxes;

import ru.shanalotte.annotations.Component;
import ru.shanalotte.annotations.MyInject;

@Component
public class InjectedClass {

    @MyInject
    TestPrototypeBox proto;

    @MyInject
    TestSingletonBox singleton;

    public void testBoxes(){
        System.out.println(proto.value);
        System.out.println(proto.value);
        System.out.println(proto.value);

        System.out.println(singleton.value);
        System.out.println(singleton.value);
        System.out.println(singleton.value);
    }
}
