package com.rftdevgroup.transporthub.ui.view;

import com.rftdevgroup.transporthub.ui.view.menu.MainMenu;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = Views.HOME)
public class HomeView extends VerticalLayout implements View {

    private Label label = new Label();

    public HomeView() {
        label.setCaption("Welcome from the home view.");
        addComponent(label);
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
