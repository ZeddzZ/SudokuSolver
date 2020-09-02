import Helpers.PropertiesHelper;
import Menu.MenuEngine;
import Menu.MenuStructure;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        PropertiesHelper.initProperties();
        MenuEngine menuEngine = new MenuEngine(MenuStructure.getMainMenu());
        menuEngine.run();
    }
}
