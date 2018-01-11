package com.rftdevgroup.transporthub.ui.view.menu;

import com.rftdevgroup.transporthub.ui.view.Views;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class MainMenu extends MenuBar {

    private Navigator navigator;
    private Authentication auth;

    public MainMenu(Navigator navigator) {
        this.navigator = navigator;
        this.auth = SecurityContextHolder.getContext().getAuthentication();
        addItem("List Transports", VaadinIcons.LIST, listTransportCommand);
        addItem(auth.getName(), VaadinIcons.USER, updateUserCommand);
        for(Object o : auth.getAuthorities())
        {
            if(o.toString().compareTo("ROLE_ADMIN") == 0)
            {
                addItem("List Users", VaadinIcons.LOCK, listUsersCommand);
            }
        }

        addItem("Logout", VaadinIcons.EXIT, logoutCommand);
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

    private MenuBar.Command logoutCommand = new Command() {
        @Override
        public void menuSelected(MenuItem menuItem) {
            SecurityContextHolder.clearContext();
            UI.getCurrent().getPage().setLocation("/transporthub/logout");
        }
    };

    private MenuBar.Command listUsersCommand = new Command() {
        @Override
        public void menuSelected(MenuItem menuItem) {
            navigator.navigateTo(Views.USER_LIST);
        }
    };
}
