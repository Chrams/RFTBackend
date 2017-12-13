package com.rftdevgroup.transporthub.ui;

import com.rftdevgroup.transporthub.data.dto.transport.TransportListViewDTO;
import com.rftdevgroup.transporthub.data.model.transport.Transport;
import com.rftdevgroup.transporthub.data.repository.transport.TransportRepository;
import com.rftdevgroup.transporthub.service.TransportService;
import com.vaadin.annotations.Theme;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import java.security.Principal;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringUI(path = "/")
@Theme("valo")
public class VaadinUI extends UI {

    private TransportService transportService;
    Grid<TransportListViewDTO> grid;
    Button testBtn;

    @Autowired
    public VaadinUI(TransportService transportService){
        this.transportService = transportService;
        this.grid = new Grid<>(TransportListViewDTO.class);
        this.testBtn = new Button("Test");
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        HorizontalLayout buttons = new HorizontalLayout(testBtn);
        VerticalLayout mainLayout = new VerticalLayout(buttons, grid);

        mainLayout.setSizeFull();

        setContent(mainLayout);

        grid.setSizeFull();
        grid.setColumns("id","cargoName","cityFrom","cityTo","description","daysRemaining","owner","currentPrice","lowestBidder");

        testBtn.addClickListener(e-> Notification.show(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString()));


        grid.setItems(transportService.listTransports());
    }
}
