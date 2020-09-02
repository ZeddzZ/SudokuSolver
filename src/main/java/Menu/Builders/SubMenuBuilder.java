package Menu.Builders;

import Menu.MenuItem;
import Menu.Menu;

public class SubMenuBuilder extends MenuBuilder implements ISubMenuBuilder {

    private final IMenuBuilder parentBuilder;

    public SubMenuBuilder(IMenuBuilder parentBuilder) {
        super(new Menu());
        this.parentBuilder = parentBuilder;
    }

    /**
     * Adds menu item to the end of current Menu
     *
     * @param item
     * Menu Item to add
     * @return
     * Instance of builder
     */
    @Override
    public ISubMenuBuilder addMenuItem(MenuItem item) {
        return (ISubMenuBuilder) super.addMenuItem(item);
    }

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
    @Override
    public ISubMenuBuilder addMenuItem(MenuItem item, int position) {
        return (ISubMenuBuilder) super.addMenuItem(item, position);
    }

    /**
     * Builds created sub menu and appending it to parent menu
     * using {@link IMenuBuilder#appendSubMenu(Menu)} method
     *
     * @return
     * Instance of Parent Menu builder
     */
    public IMenuBuilder back() {
        Menu menu = build();
        parentBuilder.appendSubMenu(menu);
        return parentBuilder;
    }
}
