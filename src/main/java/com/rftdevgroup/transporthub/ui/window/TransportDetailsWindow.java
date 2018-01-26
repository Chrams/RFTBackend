package com.rftdevgroup.transporthub.ui.window;

import com.rftdevgroup.transporthub.data.dto.auction.BidDTO;
import com.rftdevgroup.transporthub.data.dto.transport.TransportDetailsDTO;
import com.rftdevgroup.transporthub.data.model.transport.Cargo;
import com.rftdevgroup.transporthub.ui.components.CargoForm;
import com.vaadin.ui.*;
import org.modelmapper.ModelMapper;

public class TransportDetailsWindow extends Window {

    private FormLayout layout = new FormLayout();
    private TransportDetailsDTO transport;

    public TransportDetailsWindow(TransportDetailsDTO dto) {
        super("Transport Details");
        transport = dto;

        center();
        init();
        setContent(layout);

    }

    private void init() {
        layout.addComponent(new Label("Owner"));
        HorizontalLayout userLayout = new HorizontalLayout();
        TextField userName = new TextField("User Name");
        TextField firstName = new TextField("First Name");
        TextField lastName = new TextField("Last Name");
        userName.setReadOnly(true);
        userName.setValue(transport.getOwner().getUserName());
        firstName.setReadOnly(true);
        firstName.setValue(transport.getOwner().getFirstName());
        lastName.setReadOnly(true);
        lastName.setValue(transport.getOwner().getLastName());
        userLayout.addComponents(userName, firstName, lastName);
        layout.addComponent(userLayout);

        CargoForm cargo = new CargoForm();
        layout.addComponent(cargo);
        ModelMapper mapper = new ModelMapper();
        Cargo cargoFromDTO = mapper.map(transport.getCargo(), Cargo.class);
        cargo.setFormData(cargoFromDTO);

        TextField placeOfLoad = new TextField("Place of Load");
        placeOfLoad.setReadOnly(true);
        placeOfLoad.setValue(transport.getPlaceOfLoad().toString());
        layout.addComponent(placeOfLoad);

        TextField timeOfLoad = new TextField("Time of Load");
        timeOfLoad.setReadOnly(true);
        timeOfLoad.setValue(transport.getTimeOfLoad().toString());
        layout.addComponent(timeOfLoad);

        TextField placeOfUnload = new TextField("Place of Unload");
        placeOfUnload.setReadOnly(true);
        placeOfUnload.setValue(transport.getPlaceofUnload().toString());
        layout.addComponent(placeOfUnload);

        TextField timeOfUnload = new TextField("Time of unload");
        timeOfUnload.setReadOnly(true);
        timeOfUnload.setValue(transport.getTimeOfUnload().toString());
        layout.addComponent(timeOfUnload);

        Grid<BidDTO> bids = new Grid<>();
        bids.setItems(transport.getBids());
        bids.addColumn(BidDTO::getBidder).setCaption("Bidder");
        bids.addColumn(BidDTO::getAmount).setCaption("Amount");
        layout.addComponent(bids);

        Button bid = new Button("Place bid");
        layout.addComponent(bid);

        layout.setMargin(true);
    }

}
