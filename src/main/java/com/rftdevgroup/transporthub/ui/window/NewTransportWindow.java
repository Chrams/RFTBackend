package com.rftdevgroup.transporthub.ui.window;

import com.rftdevgroup.transporthub.data.model.transport.Cargo;
import com.rftdevgroup.transporthub.data.model.transport.Transport;
import com.rftdevgroup.transporthub.data.model.user.Address;
import com.rftdevgroup.transporthub.data.model.user.User;
import com.rftdevgroup.transporthub.service.TransportService;
import com.rftdevgroup.transporthub.ui.components.AddressForm;
import com.rftdevgroup.transporthub.ui.components.CargoForm;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.ui.*;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.util.ArrayList;

@Slf4j
public class NewTransportWindow extends Window {

    private Transport newTransport = new Transport();
    private Binder<Transport> binder = new Binder<>();
    private Binder<Cargo> cargoBinder;
    private Binder<Address> placeOfLoadBinder;
    private Binder<Address> placeOfUnloadBinder;
    private User owner;
    private VerticalLayout layout = new VerticalLayout();

    private TransportService transportService;

    private DateField timeOfLoad;
    private DateField timeOfUnload;

    public NewTransportWindow(TransportService transportService, User owner) {
        super("New Transport");
        this.transportService = transportService;
        this.owner = owner;
        center();
        init();
        setContent(layout);
    }

    private void init() {

        CargoForm cargo = new CargoForm();
        cargoBinder = cargo.getBinder();
        layout.addComponent(cargo);

        AddressForm placeOfLoad = new AddressForm();
        placeOfLoadBinder = placeOfLoad.getBinder();
        layout.addComponent(placeOfLoad);

        timeOfLoad = new DateField("Time of Load");
        binder.forField(timeOfLoad)
                .withValidator(time -> time.isAfter(LocalDate.now()), "Time of load should be in the future.")
                .bind(Transport::getTimeOfLoad, Transport::setTimeOfLoad);
        layout.addComponent(timeOfLoad);

        AddressForm placeOfUnload = new AddressForm();
        placeOfUnloadBinder = placeOfUnload.getBinder();
        layout.addComponent(placeOfUnload);

        timeOfUnload = new DateField("Time of Unload");
        binder.forField(timeOfUnload)
                .withValidator(time -> time.isAfter(LocalDate.now()), "Time of unload should be in the future.")
                .bind(Transport::getTimeOfUnload, Transport::setTimeOfUnload);
        layout.addComponent(timeOfUnload);

        TextField startingPrice = new TextField("Starting price");
        binder.forField(startingPrice)
                .withConverter(new StringToIntegerConverter("Provide a valid amount"))
                .withValidator(price -> price > 0, "Provide a valid amount")
                .bind(Transport::getStartingPrice, Transport::setStartingPrice);
        layout.addComponent(startingPrice);

        Button ok = new Button("Create");
        ok.addClickListener(btnClickListener);
        layout.addComponent(ok);

        layout.setMargin(true);
    }

    private Button.ClickListener btnClickListener = new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {

            try {
                Address loadAddress = new Address();
                Address unloadAddress = new Address();
                placeOfLoadBinder.writeBean(loadAddress);
                placeOfUnloadBinder.writeBean(unloadAddress);

                Cargo cargo = new Cargo();
                cargoBinder.writeBean(cargo);

                binder.writeBean(newTransport);

                newTransport.setOwner(owner);
                newTransport.setCargo(cargo);
                newTransport.setPlaceOfLoad(loadAddress);
                newTransport.setPlaceOfUnload(unloadAddress);
                newTransport.setBids(new ArrayList<>());

                log.debug("Transport to save: {}", newTransport);
                log.debug("Place of load: {}", newTransport.getPlaceOfLoad());
                log.debug("Place of unload: {}", newTransport.getPlaceOfUnload());

                Transport saved = transportService.save(newTransport);

                log.debug("saved transport: {}", saved);
                log.debug("saved Place of load: {}", saved.getPlaceOfLoad());
                log.debug("saved Place of unload: {}", saved.getPlaceOfUnload());

                Notification.show("Done.");
                UI.getCurrent().getPage().reload();
                close();
            } catch (ValidationException e) {
                e.printStackTrace();
                log.debug("Error happened: {}", e);
            }
        }
    };
}
