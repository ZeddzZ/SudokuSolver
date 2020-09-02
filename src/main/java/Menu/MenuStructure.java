package Menu;

import Helpers.MenuHelper;

import java.util.ArrayList;

public class MenuStructure {

    protected static Menu propertiesMenu = new Menu(new ArrayList<>() {{
        add(new MenuItem("Show Properties", MenuHelper::showProperties));
        add(new MenuItem("Add Property", MenuHelper::addProperty));
        add(new MenuItem("Edit Property", MenuHelper::editProperty));
        add(new MenuItem("Save Properties to file", MenuHelper::saveProperties));
    }});

    protected static Menu folderMenu = new Menu(new ArrayList<>() {{
        add(new MenuItem("Show Files in default folder", MenuHelper::showDefaultDirectoryFiles));
        add(new MenuItem("Show Files in specified folder", MenuHelper::showSpecifiedDirectoryFiles));
    }});

    protected static Menu fileMenu = new Menu(new ArrayList<>() {{
        add(new MenuItem("Open file in default folder", MenuHelper::selectFileFromDefaultDirectory));
        add(new MenuItem("Open specified file", MenuHelper::selectFileByPath));
    }});

    protected static Menu mainMenu = new Menu(new ArrayList<>() {{
                add(new MenuItem("Properties...", propertiesMenu));
                add(new MenuItem("Folder...", folderMenu));
                add(new MenuItem("File...", fileMenu));
                add(new MenuItem("Exit", () -> System.exit(0)));
            }});



    public static Menu getMainMenu() {
        populateParentMenu(mainMenu);
        return mainMenu;
    }

    public static void populateParentMenu(Menu menu) {
        if(menu.getMenuItems().size() == 0) {
            return;
        }
        for(MenuItem item: menu.getMenuItems()) {
            item.getSubMenu().setParentMenu(menu);
            populateParentMenu(item.getSubMenu());
        }
    }
}
