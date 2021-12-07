package ru.shanalotte.testboxes2;

import ru.shanalotte.annotations.ChooseName;
import ru.shanalotte.annotations.Component;
import ru.shanalotte.annotations.MyInject;

import java.util.stream.IntStream;

@Component
public class Painter {

    @MyInject
    @ChooseName(name = "monochrome")
    ColorProvider colorProvider;

    public void paintTenTimes(){
        IntStream.range(0, 10).forEach(
                i -> System.out.println(colorProvider.getColor()));
    }
}
