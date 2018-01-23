package com.rftdevgroup.transporthub.ui.view;

import com.rftdevgroup.transporthub.data.dto.transport.TransportDetailsDTO;
import com.rftdevgroup.transporthub.data.dto.transport.TransportListViewDTO;
import com.rftdevgroup.transporthub.service.TransportService;
import com.rftdevgroup.transporthub.ui.window.NewTransportWindow;
import com.rftdevgroup.transporthub.ui.window.TransportDetailsWindow;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.util.Optional;

@SpringView(name = Views.TRANSPORT_LIST)
public class TransportView extends VerticalLayout implements View {

    private TransportService transportService;

    private Grid<TransportListViewDTO> transportGrid = new Grid<>(TransportListViewDTO.class);

    @Autowired
    public TransportView(TransportService transportService) {
        this.transportService = transportService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        Notification.show("Transport lists.");
    }

    @PostConstruct
    private void init() {
        HorizontalLayout buttons = new HorizontalLayout();
        Button newTransport = new Button("New Transport", VaadinIcons.PLUS);
        newTransport.addClickListener(newTransportClickListener);
        buttons.addComponent(newTransport);
        addComponent(buttons);

        transportGrid.addItemClickListener(gridItemClickListener);
        transportGrid.setSizeFull();
        transportGrid.setColumns("id", "cargoName", "cityFrom", "cityTo", "description", "daysRemaining", "owner", "currentPrice", "lowestBidder");
        if (transportService == null) {
            System.err.println("TRANSPORT SERVICE IS NULL!! DAFQ");
        } else {
            transportGrid.setItems(transportService.listTransports());
        }
        transportGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        addComponent(transportGrid);
    }

    private ItemClickListener<TransportListViewDTO> gridItemClickListener = new ItemClickListener<TransportListViewDTO>() {
        @Override
        public void itemClick(Grid.ItemClick<TransportListViewDTO> itemClick) {
            if(itemClick.getMouseEventDetails().isDoubleClick()){
                TransportListViewDTO selected = itemClick.getItem();
                Optional<TransportDetailsDTO> detailsDTO = transportService.findAndMapTransport(selected.getId(), TransportDetailsDTO.class);
                if(detailsDTO.isPresent()){
                    TransportDetailsWindow detailsWindow = new TransportDetailsWindow(detailsDTO.get());
                    UI.getCurrent().addWindow(detailsWindow);
                } else{
                    Notification.show("Cannot find selected transport job.");
                }
            }
        }
    };


    private Button.ClickListener newTransportClickListener = new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            NewTransportWindow newTransportWindow = new NewTransportWindow();
            UI.getCurrent().addWindow(newTransportWindow);
        }
    };
}
