package Menu;

import Helpers.MathHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuEngine {
    protected List<Menu> usedMenus;
    protected Menu activeMenu;

    public MenuEngine(Menu initialMenu) {
        usedMenus = new ArrayList<Menu>();
        usedMenus.add(initialMenu);
        activeMenu = initialMenu;
    }

    public void setActiveMenu(Menu newMenu) {
        if(!usedMenus.contains(newMenu)) {
            usedMenus.add(newMenu);
        }
        activeMenu = newMenu;
        run();
    }

    public void run() {
        if(activeMenu.getMenuItems().size() == 0) {
            setActiveMenu(activeMenu.parentMenu);
        }
        activeMenu.showMenu();
        selectMenuItem();
    }

    public void back() {
        if (activeMenu.parentMenu == null) {
            System.out.println("You are already on top level menu!");
        } else {
            setActiveMenu(activeMenu.parentMenu);
        }
    }

    public void selectMenuItem() {
        boolean isValueRead = false;
        Scanner scanner = new Scanner(System.in);
        while (!isValueRead) {
            System.out.println();
            System.out.print("Select menu item: ");
            String selected = getValue(scanner);
            if (!MathHelper.tryParseInt(selected)) {
                System.out.println("Can't parse your answer! Please, pass integer value that represents number of menu item.");
            } else {
                int selectedItem = Integer.parseInt(selected);
                if (selectedItem <= 0 || selectedItem > getMaxValidValue()) {
                    System.out.println("Received menu item is out of borders! Please, select value from 1 to " + getMaxValidValue());
                } else {
                    isValueRead = true;
                    //Return to 0-based counting
                    initMenuItemAction(selectedItem - 1);
                }
            }
        }
    }

    protected String getValue(Scanner scanner) {
        String value = scanner.nextLine();
        return value;
    }

    protected int getMaxValidValue() {
        int menuSize = activeMenu.getMenuItems().size();
        if (activeMenu.parentMenu == null) {
            return menuSize;
        }
        // Added "Back" value;
        return menuSize + 1;
    }

    protected void initMenuItemAction(int selectedItem) {
        if (activeMenu.parentMenu != null) {
            if (selectedItem == activeMenu.getMenuItems().size()) {
                //Selected "Back" value;
                back();
                return;
            }
        }
        activeMenu.getMenuItems().get(selectedItem).doAction();
        setActiveMenu(activeMenu.getMenuItems().get(selectedItem).subMenu);
    }
}
