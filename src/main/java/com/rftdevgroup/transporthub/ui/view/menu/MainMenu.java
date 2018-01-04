package com.rftdevgroup.transporthub.ui.view.menu;

import com.rftdevgroup.transporthub.ui.view.Views;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.MenuBar;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class MainMenu extends MenuBar {

    private Navigator navigator;
    private Authentication auth;

    public MainMenu(Navigator navigator) {
        this.navigator = navigator;
        this.auth = SecurityContextHolder.getContext().getAuthentication();
        addItem("List Transports", VaadinIcons.LIST, listTransportCommand);
        addItem(auth.getName(), VaadinIcons.USER, updateUserCommand);
    }

    private MenuBar.Command listTransportCommand = new Command() {
        @Override
        public void menuSelected(MenuItem menuItem) {
            navigator.navigateTo(Views.TRANSPORT_LIST);
        }
    };

    private MenuBar.Command updateUserCommand = new Command() {
        @Override
        public void menuSelected(MenuItem menuItem) {
            navigator.navigateTo(Views.USER_DETAILS);
        }
    };
}
