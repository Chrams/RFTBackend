package com.rftdevgroup.transporthub.ui;

import com.rftdevgroup.transporthub.service.TransportService;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI(path = "/")
@Theme("valo")
public class VaadinUI extends UI {

    private SpringNavigator navigator;
    private SpringViewProvider viewProvider;

    @Autowired
    public VaadinUI(TransportService transportService, SpringViewProvider viewProvider) {
        this.viewProvider = viewProvider;
        navigator = new SpringNavigator(this, this);
        navigator.addProvider(viewProvider);
        setNavigator(navigator);
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        getPage().setTitle("Main view");
        navigator.navigateTo("transportView");
    }
}
