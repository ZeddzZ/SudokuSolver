package Menu;

import Helpers.MenuHelper;
import Menu.Builders.IMenuBuilder;
import Menu.Builders.MenuBuilder;

public class MenuStructure {
    public static Menu getMainMenu() {
        IMenuBuilder menuBuilder = new MenuBuilder()
                .addMenuItem(new MenuItem("Properties..."))
                .addSubMenu()
                    .addMenuItem(new MenuItem("Show Properties", MenuHelper::showProperties))
                    .addMenuItem(new MenuItem("Add Property", MenuHelper::addProperty))
                    .addMenuItem(new MenuItem("Edit Property", MenuHelper::editProperty))
                    .addMenuItem(new MenuItem("Save Properties to file", MenuHelper::saveProperties))
                    .back()
                .addMenuItem(new MenuItem("Folder..."))
                .addSubMenu()
                    .addMenuItem(new MenuItem("Show Files in default folder", MenuHelper::showDefaultDirectoryFiles))
                    .addMenuItem(new MenuItem("Show Files in specified folder", MenuHelper::showSpecifiedDirectoryFiles))
                    .back()
                .addMenuItem(new MenuItem("File..."))
                .addSubMenu()
                    .addMenuItem(new MenuItem("Open file in default folder", MenuHelper::selectFileFromDefaultDirectory))
                    .addMenuItem(new MenuItem("Open specified file", MenuHelper::selectFileByPath))
                    .back()
                .addMenuItem(new MenuItem("Exit", MenuHelper::exitApplication));
        return menuBuilder.build();
    }
}
