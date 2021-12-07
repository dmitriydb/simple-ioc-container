package ru.shanalotte.container;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import ru.shanalotte.annotations.*;
import ru.shanalotte.domain.Box;
import ru.shanalotte.testboxes.InjectedClass;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс, реализующий логику IoC-контейнера
 * Управляет внедряемыми объектами (Box) и компонентами приложения (Component)
 *
 * @version 1.0
 * @since 1.0
 */
public class IoCContainer {

    /**
     * Карта singleton объектов
     * Содержит по одному объекту на каждый класс и возвращает этот объект по запросу
     */
    private static Map<Class, Object> singletons = new HashMap<>();

    /**
     * Карта, которая отображает пользовательские имена Box-ов на их классы
     */
    private static Map<String, Class> boxesByName = new HashMap<>();

    /**
     * Множество поддерживаемых Box-ов
     */
    private static Set<Box> supportedBoxes = new HashSet<>();

    /**
     * Множество поддерживаемых компонентов
     */
    private static Set<Class> componentClasses = new HashSet<>();

    /**
     * Загружает Box по классу
     * <p>
     * Если был запрошен @Component, то
     * объект соответствующего компонента инициализируется и
     * возвращается, иначе инициализируется и возвращается объект Box
     *
     * @param clazz
     * @return
     */
    public static Object getBox(Class clazz) {
        if (componentClasses.contains(clazz))
            return initializeComponent(clazz);
        else
            return getBoxObjectByClass(clazz);
    }

    /**
     * Метод возвращает объект Box по пользовательскому имени
     * В данный момент пользовательские имена поддерживаются только для аннотации Box
     *
     * @param name
     * @return
     * @since 1.0
     * todo: добавить поддержку имен для @Component
     * todo: пользовательские исключения повсеместно
     */
    public static Object getBox(String name) {
        Class clazz = boxesByName.entrySet().stream().filter(b -> b.getKey().equals(name)).findFirst().get().getValue();
        return getBoxObjectByClass(clazz);
    }

    /**
     * Метод должен находить и обрабатывать внедряемые объекты и компоненты во всех пакетах в classpath
     * В данный момент обрабатываются только основные пакеты верхнего уровня: ru, net, org, com
     *
     * @since 1.0
     */
    public static void loadBoxes() {
        findBoxesInPackage("ru");
        findBoxesInPackage("com");
        findBoxesInPackage("org");
        findBoxesInPackage("net");
        loadComponents("ru");
        loadComponents("com");
        loadComponents("org");
        loadComponents("net");
    }

    /**
     * Загружает компоненты из пакета верхнего уровня
     * В данный момент компоненты нельзя внедрять в другие Box-ы
     *
     * @param packageRoot
     * @since 1.0
     * todo: внедрение компонентов в другие компоненты
     */
    private static void loadComponents(String packageRoot) {
        Reflections reflections = new Reflections(packageRoot,
                new SubTypesScanner(false));
        Set<Class<? extends Object>> allClasses =
                reflections.getSubTypesOf(Object.class);
        for (Class c : allClasses) {
            Component component = (Component) c.getAnnotation(Component.class);
            if (component != null) componentClasses.add(c);
        }
    }

    /**
     * Инициализирует компонент, инжектит в него Box-ы и возвращает объект
     *
     * @param c класс запрашиваемого компонента
     * @return
     * @since 1.0
     */
    private static Object initializeComponent(Class c) {
        Object object;
        Field[] fields;
        try {
            object = c.newInstance();
            fields = c.getDeclaredFields();
        } catch (NoClassDefFoundError ex) {
            return null;
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
        boolean injected = false;
        for (Field f : fields) {
            MyInject inject = f.getAnnotation(MyInject.class);
            ChooseName chooseName = f.getAnnotation(ChooseName.class);

            if (inject != null) {
                Class desiredClass = f.getType();
                if (chooseName != null) {
                    String qualifierName = chooseName.name();
                    desiredClass = boxesByName.get(qualifierName);
                }
                try {
                    injected = true;
                    f.setAccessible(true);
                    f.set(object, getBoxObjectByClass(desiredClass));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return object;
    }

    /**
     * Возвращает объект Box с классом clazz
     * Если тип Box-а = prototype, то создается новый объект
     * Иначе возвращается сохраненный ранее объект синглтона
     *
     * @param clazz
     * @return
     * @since 1.0
     */
    private static Object getBoxObjectByClass(Class clazz) {
        List<Box> boxList = supportedBoxes.stream().filter(e -> (e.getClazz().equals(clazz) || clazz.isAssignableFrom(e.getClazz()))).collect(Collectors.toList());
        Box box = boxList.stream().filter(b -> b.isPrimary()).findFirst().orElse(
                null
        );
        if (box == null) {
            box = boxList.stream().findFirst().orElse(null);
        }

        if (box.getType().equals(BoxType.SINGLETON))
            return singletons.get(box.getClazz());
        try {
            return box.getClazz().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Загружает box-ы из пакета, кешируя синглтон объекты
     *
     * @param packageRoot
     * @since 1.0
     */
    private static void findBoxesInPackage(String packageRoot) {
        Reflections reflections = new Reflections(packageRoot,
                new SubTypesScanner(false));
        Set<Class<? extends Object>> allClasses =
                reflections.getSubTypesOf(Object.class);
        for (Class c : allClasses) {
            //загружаем бины
            ru.shanalotte.annotations.Box annotation = (ru.shanalotte.annotations.Box) c.getAnnotation(ru.shanalotte.annotations.Box.class);
            Primary primary = (Primary) c.getAnnotation(Primary.class);
            if (annotation != null) {
                String name = annotation.name();
                BoxType type = annotation.type();

                Class clazz = c;
                Box box = new Box();
                box.setClazz(clazz);
                box.setName(name);
                box.setType(type);
                if (primary != null) box.setPrimary(true);
                supportedBoxes.add(box);
                boxesByName.put(name, clazz);
                if (type.equals(BoxType.SINGLETON)) {
                    try {
                        Object singletonObject = clazz.newInstance();
                        singletons.put(clazz, singletonObject);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
