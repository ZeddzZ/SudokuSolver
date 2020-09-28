package Menu.Builders;

import Loggers.IShower;
import Menu.Menu;
import Menu.MenuItem;

public interface IMenuBuilder {

    /**
     * Sets class to be responsible for showing menu items
     *
     * @param shower
     * Instance of class that will show menu items
     * @return
     * Instance of builder
     */
    IMenuBuilder setMenuShower(IShower shower);

    /**
     * Adds menu item to the end of current Menu
     *
     * @param item
     * Menu Item to add
     * @return
     * Instance of builder
     */
    IMenuBuilder addMenuItem(MenuItem item);

    /**
     * Adds menu item to the specified position of current Menu
     * if position < 0 - places to 0 position
     * if position > size - places to the end of menu
     * else - places to specified position
     *
     * @param item
     * Menu Item to add
     * @param position
     * position in the Menu
     * @return
     * Instance of builder
     */
    IMenuBuilder addMenuItem(MenuItem item, int position);

    /**
     * Initiates creation of Sub Menu
     *
     * @return
     * Instance of ISubMenuBuilder
     */
    ISubMenuBuilder addSubMenu();

    /**
     * Appends created sub menu to last added menu item
     *
     * @param menu
     * Sub menu to append
     */
    void appendSubMenu(Menu menu);

    /**
     * Creates an Instance of Menu with specified in builder params
     *
     * @return
     * Instance of Menu
     */
    Menu build();
}
