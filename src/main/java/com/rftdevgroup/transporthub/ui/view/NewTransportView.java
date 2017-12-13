package com.rftdevgroup.transporthub.ui.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class NewTransportView extends VerticalLayout implements View {

    private Navigator navigator;
    private FormLayout formLayout;

    public NewTransportView(Navigator navigator) {
        this.navigator = navigator;
        this.formLayout = new FormLayout();
        addComponent(formLayout);
        init();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    private void init() {
        TextField tf1 = new TextField("Name");
        tf1.setRequiredIndicatorVisible(true);
        formLayout.addComponent(tf1);
    }
}
