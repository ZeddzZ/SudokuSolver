package Menu;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    protected Menu parentMenu;
    protected List<MenuItem> menuItems;

    public Menu(List<MenuItem> menuItems, Menu parentMenu) {
        this.menuItems = menuItems;
        this.parentMenu = parentMenu;
    }

    public Menu(List<MenuItem> menuItems) {
        this(menuItems, null);
    }

    public Menu() {
       this(new ArrayList<>(), null);
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    /**
     * Sets parent menu for current Menu to provide vertical navigation
     * @param parentMenu
     * Menu which menu item has that sub menu
     */
    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    /**
     * Adds menu item to the end of current Menu
     * @param item
     * Menu Item to add
     */
    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
    }

    /**
     * Adds menu item to the specified position of current Menu
     * if position < 0 - places to 0 position
     * if position > size - places to the end of menu
     * else - places to specified position
     * @param item
     * Menu Item to add
     * @param position
     * position in the Menu
     */
    public void addMenuItem(MenuItem item, int position) {
        if (position >= menuItems.size()) {
            addMenuItem(item);
            return;
        }
        if (position <= 0) {
            menuItems.add(0, item);
            return;
        }
        menuItems.add(position, item);
    }

    /**
     * Prints current menu to console
     */
    public void showMenu() {
        System.out.println();
        for(int i = 0; i < menuItems.size(); i++) {
            System.out.println( (i + 1) + ". " + menuItems.get(i).getName());
        }
        if(parentMenu != null) {
            System.out.println( (menuItems.size() + 1) + ". Back...");
        }
        System.out.println();
    }




}
