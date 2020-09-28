package Menu;

import Helpers.MenuHelper;
import Loggers.ConsoleShower;
import Loggers.IShower;
import Menu.Builders.IMenuBuilder;
import Menu.Builders.MenuBuilder;

public class MenuStructure {
    public static Menu getMainMenu() {
        IShower menuShower = new ConsoleShower();
        IMenuBuilder menuBuilder = new MenuBuilder()
                .setMenuShower(menuShower)
                .addMenuItem(new MenuItem("Properties..."))
                .addSubMenu()
                    .addMenuItem(new MenuItem("Show Properties", () -> MenuHelper.showProperties(menuShower)))
                    .addMenuItem(new MenuItem("Add Property", () -> MenuHelper.addProperty(menuShower)))
                    .addMenuItem(new MenuItem("Edit Property", () -> MenuHelper.editProperty(menuShower)))
                    .addMenuItem(new MenuItem("Save Properties to file", () -> MenuHelper.saveProperties(menuShower)))
                    .back()
                .addMenuItem(new MenuItem("Folder..."))
                .addSubMenu()
                    .addMenuItem(new MenuItem("Show Files in default folder", () -> MenuHelper.showDefaultDirectoryFiles(menuShower)))
                    .addMenuItem(new MenuItem("Show Files in specified folder", () -> MenuHelper.showSpecifiedDirectoryFiles(menuShower)))
                    .back()
                .addMenuItem(new MenuItem("File..."))
                .addSubMenu()
                    .addMenuItem(new MenuItem("Open file in default folder", () -> MenuHelper.selectFileFromDefaultDirectory(menuShower)))
                    .addMenuItem(new MenuItem("Open specified file", () -> MenuHelper.selectFileByPath(menuShower)))
                    .back()
                .addMenuItem(new MenuItem("Exit", () -> MenuHelper.exitApplication(menuShower)));
        return menuBuilder.build();
    }
}
