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

    /**
     * Sets specified menu as active menu
     * If it is first call for this menu, adds it to usedMenus List
     * If not, takes it from usedMenus List
     * This approach simplifies vertical navigation from sub menus to parent menus
     *
     * @param newMenu
     * Menu to become active
     */
    public void setActiveMenu(Menu newMenu) {
        if(!usedMenus.contains(newMenu)) {
            usedMenus.add(newMenu);
        }
        activeMenu = newMenu;
        run();
    }

    /**
     * Showing items of active menu and initiating request for menu item selection
     */
    public void run() {
        if(activeMenu.getMenuItems().size() == 0) {
            setActiveMenu(activeMenu.parentMenu);
        }
        activeMenu.showMenu();
        selectMenuItem();
    }

    /**
     * Going back from submenu to parent menu by setting last as active menu
     */
    public void back() {
        if (activeMenu.parentMenu == null) {
            System.out.println("You are already on top level menu!");
        } else {
            setActiveMenu(activeMenu.parentMenu);
        }
    }

    /**
     * Reading menu item number from console and selecting it
     * It means executing its action (if exists) and showing sub menu for this item (if exists)
     */
    public void selectMenuItem() {
        boolean isValueRead = false;
        Scanner scanner = new Scanner(System.in);
        while (!isValueRead) {
            System.out.println();
            System.out.print("Select menu item: ");
            String selected = scanner.nextLine();
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

    /**
     * Selecting menu item by its position
     *
     * @param selectedItem
     * Position of menu item
     */
    public void initMenuItemAction(int selectedItem) {
        if (activeMenu.parentMenu != null) {
            if (selectedItem == activeMenu.getMenuItems().size()) {
                //Selected "Back" value;
                back();
                return;
            }
        }
        activeMenu.getMenuItems().get(selectedItem).doAction();
        Menu subMenu = activeMenu.getMenuItems().get(selectedItem).subMenu;
        if(subMenu.menuItems.size() > 0) {
            setActiveMenu(activeMenu.getMenuItems().get(selectedItem).subMenu);
        } else {
            setActiveMenu(activeMenu);
        }
    }

    protected int getMaxValidValue() {
        int menuSize = activeMenu.getMenuItems().size();
        if (activeMenu.parentMenu == null) {
            return menuSize;
        }
        // Added "Back" value;
        return menuSize + 1;
    }

}
