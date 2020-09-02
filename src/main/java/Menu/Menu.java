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

    public Menu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }
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
