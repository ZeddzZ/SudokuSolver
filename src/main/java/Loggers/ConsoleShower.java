package Loggers;

import java.util.Arrays;

public class ConsoleShower implements IShower {

    public void show(Object objectToShow) {
        System.out.println(objectToShow);
    }
    public void show() {
        show("");}

    public void show(Object... objects) {
        System.out.println(Arrays.toString(objects));
    }
}
