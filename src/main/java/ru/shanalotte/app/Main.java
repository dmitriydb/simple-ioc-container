package ru.shanalotte.app;

import ru.shanalotte.container.IoCContainer;
import ru.shanalotte.testboxes2.ColorProvider;
import ru.shanalotte.testboxes2.Painter;

/**
 * Класс для тестирования работы IoC контейнера
 */
public class Main {

    public static void main(String[] args){
        IoCContainer.loadBoxes();

        /*Painter p = (Painter)IoCContainer.getBox(Painter.class);
        p.*paintTenTimes();*/

        ColorProvider colorProvider = (ColorProvider)IoCContainer.getBox(ColorProvider.class);
        System.out.println(colorProvider.getColor());

   }
}
