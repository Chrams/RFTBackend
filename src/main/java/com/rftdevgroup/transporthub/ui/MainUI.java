package com.rftdevgroup.transporthub.ui;

import com.rftdevgroup.transporthub.ui.view.Views;
import com.rftdevgroup.transporthub.ui.view.menu.MainMenu;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@SpringUI(path = "/")
@Theme("valo")
public class MainUI extends UI {

    @Value("${ui.main.title}")
    private static String PAGE_TITLE;

    @Autowired
    private SpringViewProvider viewProvider;

    private VerticalLayout layout = new VerticalLayout();
    private VerticalLayout container = new VerticalLayout();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(layout);
        Navigator navigator = new Navigator(this, container);
        navigator.addProvider(viewProvider);
        setNavigator(navigator);
        layout.addComponent(new MainMenu(getNavigator()));
        layout.addComponent(container);
        getPage().setTitle(PAGE_TITLE);
        getNavigator().navigateTo(Views.HOME_VIEW.toString());
    }
}
