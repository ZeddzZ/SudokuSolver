package Menu.Builders;

import Menu.Menu;
import Menu.MenuItem;

public class MenuBuilder implements IMenuBuilder {

    protected Menu menu;
    protected MenuItem lastAddedMenuItem;

    public MenuBuilder() {
        this(new Menu());
    }

    public MenuBuilder(Menu menu) {
        this.menu = menu;
        lastAddedMenuItem =
                menu.getMenuItems().size() == 0
                ? null
                : menu.getMenuItems().get(menu.getMenuItems().size() - 1);
    }

    /**
     * Adds menu item to the end of current Menu
     * @param item
     * Menu Item to add
     * @return
     * Instance of builder
     */
    public IMenuBuilder addMenuItem(MenuItem item) {
        return addMenuItem(item, menu.getMenuItems().size());
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
     * @return
     * Instance of builder
     */
    public IMenuBuilder addMenuItem(MenuItem item, int position) {
        menu.addMenuItem(item, position);
        lastAddedMenuItem = item;
        return this;
    }

    /**
     * Initiates creation of Sub Menu
     * @return
     * Instance of ISubMenuBuilder
     */
    public ISubMenuBuilder addSubMenu() {
        return new SubMenuBuilder(this);
    }

    /**
     * Appends created sub menu to last added menu item
     * @param menu
     * Sub menu to append
     */
    public void appendSubMenu(Menu menu) {
        lastAddedMenuItem.setSubMenu(menu);
        menu.setParentMenu(this.menu);
    }

    /**
     * Creates an Instance of Menu with specified in builder params
     * @return
     * Instance of Menu
     */
    public Menu build() {
        return menu;
    }
}
