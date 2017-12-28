package com.rftdevgroup.transporthub.ui.view;

import com.rftdevgroup.transporthub.data.dto.transport.TransportListViewDTO;
import com.rftdevgroup.transporthub.service.TransportService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Grid;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = "transportView")
public class TransportView extends VerticalLayout implements View {

    private TransportService transportService;
    private Grid<TransportListViewDTO> transportGrid;
    private MenuBar menu;
    private SpringNavigator navigator;

    public TransportView(TransportService transportService, SpringNavigator navigator) {
        this.transportService = transportService;
        this.navigator = navigator;
        this.transportGrid = new Grid<>(TransportListViewDTO.class);
        this.menu = new MenuBar();
        init();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        Notification.show("Transport lists.");
    }

    private void init() {
        addComponent(menu);
        menu.addItem("New Transport", VaadinIcons.PLUS, createTransportCommand);

        transportGrid.setSizeFull();
        transportGrid.setColumns("id", "cargoName", "cityFrom", "cityTo", "description", "daysRemaining", "owner", "currentPrice", "lowestBidder");
        transportGrid.setItems(transportService.listTransports());
        addComponent(transportGrid);
    }

    private MenuBar.Command createTransportCommand = new MenuBar.Command() {
        @Override
        public void menuSelected(MenuBar.MenuItem menuItem) {
            navigator.navigateTo("newTransportView");
        }
    };
}
