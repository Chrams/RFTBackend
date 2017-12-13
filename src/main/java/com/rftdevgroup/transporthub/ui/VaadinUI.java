package com.rftdevgroup.transporthub.ui;

import com.rftdevgroup.transporthub.service.TransportService;
import com.rftdevgroup.transporthub.ui.view.NewTransportView;
import com.rftdevgroup.transporthub.ui.view.TransportView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI(path = "/")
@Theme("valo")
public class VaadinUI extends UI {

    private Navigator navigator;

    @Autowired
    public VaadinUI(TransportService transportService) {
        navigator = new Navigator(this, this);
        navigator.addView("", new TransportView(transportService, navigator));
        navigator.addView("newTransport", new NewTransportView(navigator));
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        getPage().setTitle("Main view");
        navigator.navigateTo("");
    }
}
