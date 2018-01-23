package com.rftdevgroup.transporthub.ui.window;

import com.rftdevgroup.transporthub.data.dto.transport.TransportCreateDTO;
import com.vaadin.data.Binder;
import com.vaadin.ui.*;

public class NewTransportWindow extends Window {

    private TransportCreateDTO newTransport = new TransportCreateDTO();
    private Binder<TransportCreateDTO> binder = new Binder<>();
    private FormLayout layout = new FormLayout();

    private TextField cargoName = new TextField("Cargo Name");
    private TextField cargoDescription = new TextField("Cargo Description");
    private TextField cargoWeight = new TextField("Cargo Weight");
    private TextField cargoWidth = new TextField("Cargo Width");
    private TextField cargoHeight = new TextField("Cargo Height");
    private TextField cargoDepth = new TextField("Cargo Depth");



    public NewTransportWindow() {
        super("New Transport");
        center();
        init();
        setContent(layout);
    }

    private void init() {
        VerticalLayout cargo = new VerticalLayout();
        cargo.addComponent(new Label("Cargo:"));

        HorizontalLayout l1 = new HorizontalLayout();
        l1.addComponents(cargoName, cargoDescription);
        cargo.addComponent(l1);

        HorizontalLayout l2 = new HorizontalLayout();
        l2.addComponents(cargoWeight, cargoWidth, cargoHeight, cargoDepth);
        cargo.addComponent(l2);

        layout.addComponent(cargo);


        layout.setMargin(true);
    }
}
