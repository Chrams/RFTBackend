package com.rftdevgroup.transporthub.ui.view;

import com.rftdevgroup.transporthub.data.dto.transport.TransportListViewDTO;
import com.rftdevgroup.transporthub.service.TransportService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = "transportView")
public class TransportView extends VerticalLayout implements View {

    private TransportService transportService;

    private Grid<TransportListViewDTO> transportGrid = new Grid<>(TransportListViewDTO.class);

    @Autowired
    public TransportView(TransportService transportService) {
        this.transportService = transportService;
        init();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        Notification.show("Transport lists.");
    }

    private void init() {
        transportGrid.setSizeFull();
        transportGrid.setColumns("id", "cargoName", "cityFrom", "cityTo", "description", "daysRemaining", "owner", "currentPrice", "lowestBidder");
        if (transportService == null) {
            System.err.println("TRANSPORT SERVICE IS NULL!! DAFQ");
        } else {
            transportGrid.setItems(transportService.listTransports());
        }

        addComponent(transportGrid);
    }
}
