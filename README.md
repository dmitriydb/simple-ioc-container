# Реализация простого IoC-контейнера с помощью Dependency Injection
## Описание
Маленький фреймворк, реализующий Inversion of Control-контейнер и управление java-объектами и компонентами приложения.
Внедряемые объекты в контексте фремйворка называются Box (Boxes).

## TODO
- [x] поддержка пользовательских имен для `@Component`
- [x] пользовательские исключения
- [x] возможность внедрять компоненты в другие компоненты
- [x] предварительная инициализация 
- [x] отложенная инициализация
- [ ] распространение внедрения с уровня полей на уровень конструкторов и сеттеров
- [ ] полное сканирование всего classpath

## Установка
Для добавления в ваш проект необходимо выполнить
```
mvn install
```
Затем добавить зависимость в pom.xml:
```xml
<dependency>
            <groupId>ru.shanalotte</groupId>
            <artifactId>MyDI</artifactId>
            <version>1.1</version>
</dependency>
```
***
## Как использовать
### 1. Определите внедряемые объекты
В качестве примера создадим абстрактный класс ColorProvider, генерирующий строку с цветом:
```java
public abstract class ColorProvider {
    public abstract String getColor();
}
```
И две реализации:
```java
@Box(name="monochrome")
public class MonochromeColorProvider extends ColorProvider{
    @Override
    public String getColor() {
        return ThreadLocalRandom.current().nextInt(10) < 5 ? "black" : "white";
    }
}
```
Реализация, которая случайным образом возвращает черный или белый цвет,
```java
@Primary
@Box(name="dull")
public class RedColorProvider extends ColorProvider{
    private String color = "red";
    @Override
    public String getColor() {
        return color;
    }
}
```
И реализация, которая всегда возвращает красный цвет.

Чтобы указать пользовательское имя внедряемого объекта, нужно передать его в элемент `name` аннотации `@Box`:

`@Box(name="dull")`

Тип внедряемого объекта указывается с помощью элемента `BoxType` (по умолчанию = `SINGLETON`):

`@Box(name="dull", type = BoxType.PROTOTYPE)`

И наконец, для устранения неоднозначности внедрения по типу можно использовать аннотацию `@Primary`, которая аналогична одноименной аннотации в Spring.

В примере выше класс `RedColorProvider` будет внедряться по умолчанию.

### 2. Определите компоненты приложения
Компонентами являются объекты, в которые внедряются Box-ы.
Создадим компонент, использующий работу класса `ColorProvider`:
```java
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
```
Для указания IoC-контейнеру что класс является компонентом, необходимо пометить класс аннотацией `@Component`.

Аннотация `@MyInject` в версии 1.0 внедряет объект только на уровне полей.

Аннотация `@ChooseName` похожа на `@Qualifier` в Spring и указывает IoC-контейнеру на пользовательское имя внедряемого объекта.

### 3. Инициализируйте IoC-контейнер
Достаточно вызвать из пользовательского кода метод:
 ```java
 IoCContainer.loadBoxes();
 ```
 
 **В версии 1.0 фреймворк сканирует только пакеты, находящиеся в пакетах верхнего уровня _ru, com, net, org_**
 
 ### 4. Получаем компонент из IoC-контейнера
 ```java
 Painter p = (Painter)IoCContainer.getBox(Painter.class);
 p.paintTenTimes();
 ```
 
 Результат работы:
 ```java
 white
black
white
white
black
white
white
white
white
black
```

### 5. Или получаем объект Box
```java
ColorProvider colorProvider = (ColorProvider)IoCContainer.getBox("dull");
System.out.println(colorProvider.getColor());
```

Результат:
```java
red
```
