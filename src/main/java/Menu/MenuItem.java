package Menu;

public class MenuItem {
    protected String name;
    protected Menu subMenu;
    protected Runnable action;

    public MenuItem(String name, Runnable action, Menu subMenu) {
        this.name = name;
        this.action = action;
        this.subMenu = subMenu;
    }

    public MenuItem(String name, Menu subMenu) {
        this(name, null, subMenu);
    }

    public MenuItem(String name) {
       this(name, null, new Menu());
    }

    public MenuItem(String name, Runnable action) {
        this(name, action, new Menu());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Menu getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(Menu subMenu) {
        this.subMenu = subMenu;
    }

    public Runnable getAction() {
        return action;
    }

    public void setAction(Runnable action) {
        this.action = action;
    }

    /**
     * Performs action for this Menu Item (if it was set)
     */
    public void doAction() {
        if (action != null) {
            action.run();
        }
    }
}
