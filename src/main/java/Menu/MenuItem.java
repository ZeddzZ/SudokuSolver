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

    public Menu getSubMenu() {
        return subMenu;
    }

    public Runnable getAction() {
        return action;
    }

    public void doAction() {
        if (action != null) {
            action.run();
        }
    }
}
