package Menu.Builders;

import Menu.Menu;
import Menu.MenuItem;

public interface ISubMenuBuilder extends IMenuBuilder {

    /**
     * Adds menu item to the end of current Menu
     * @param item
     * Menu Item to add
     * @return
     * Instance of builder
     */
    @Override
    ISubMenuBuilder addMenuItem(MenuItem item);

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
    @Override
    ISubMenuBuilder addMenuItem(MenuItem item, int position);

    /**
     * Builds created sub menu and appending it to parent menu
     * using {@link IMenuBuilder#appendSubMenu(Menu)} method
     * @return
     * Instance of Parent Menu builder
     */
    IMenuBuilder back();
}
