package com.rftdevgroup.transporthub.ui.window;

import com.rftdevgroup.transporthub.data.dto.auction.BidDTO;
import com.rftdevgroup.transporthub.data.dto.transport.TransportDetailsDTO;
import com.vaadin.ui.*;

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

        layout.addComponent(new Label("Cargo"));
        HorizontalLayout cargo1Layout = new HorizontalLayout();
        HorizontalLayout cargo2Layout = new HorizontalLayout();
        TextField cargoName = new TextField("Name");
        cargoName.setReadOnly(true);
        cargoName.setValue(transport.getCargo().getName());
        TextField cargoDescription = new TextField("Description");
        cargoDescription.setReadOnly(true);
        cargoDescription.setValue(transport.getCargo().getDescription());
        TextField cargoWeight = new TextField("Weight");
        cargoWeight.setReadOnly(true);
        cargoWeight.setValue(Long.toString(transport.getCargo().getWeight()));
        TextField cargoWidth = new TextField("Width");
        cargoWidth.setReadOnly(true);
        cargoWidth.setValue(Double.toString(transport.getCargo().getWidth()));
        TextField cargoHeight = new TextField("Height");
        cargoHeight.setReadOnly(true);
        cargoHeight.setValue(Double.toString(transport.getCargo().getHeight()));
        TextField cargoDepth = new TextField("Depth");
        cargoDepth.setReadOnly(true);
        cargoDepth.setValue(Double.toString(transport.getCargo().getDepth()));
        cargo1Layout.addComponents(cargoName, cargoDescription, cargoWeight);
        layout.addComponent(cargo1Layout);
        cargo2Layout.addComponents(cargoWidth, cargoHeight, cargoDepth);
        layout.addComponent(cargo2Layout);

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
