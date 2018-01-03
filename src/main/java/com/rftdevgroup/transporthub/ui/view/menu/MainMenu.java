package com.rftdevgroup.transporthub.ui.view.menu;

import com.rftdevgroup.transporthub.ui.view.Views;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.MenuBar;

public class MainMenu extends MenuBar {

    private Navigator navigator;

    public MainMenu(Navigator navigator) {
        this.navigator = navigator;
        addItem("List Transports", VaadinIcons.LIST, listTransportCommand);
    }

    private MenuBar.Command listTransportCommand = new Command() {
        @Override
        public void menuSelected(MenuItem menuItem) {
            navigator.navigateTo(Views.LIST_TRANSPORT_VIEW.toString());
        }
    };
}
