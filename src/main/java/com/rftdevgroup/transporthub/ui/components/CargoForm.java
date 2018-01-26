package com.rftdevgroup.transporthub.ui.components;

import com.rftdevgroup.transporthub.data.model.transport.Cargo;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class CargoForm extends VerticalLayout {

    private TextField cargoNameField = new TextField("Name");
    private TextField cargoDescriptionField = new TextField("Description");
    private TextField cargoWeightField = new TextField("Weight");
    private TextField cargoWidthField = new TextField("Width");
    private TextField cargoHeightField = new TextField("Height");
    private TextField cargoDepthField = new TextField("Depth");

    private Binder<Cargo> cargoBinder = new Binder<>();

    public CargoForm() {
        init();
        setSizeFull();
    }

    private void init() {
        addComponent(new Label("Cargo"));

        HorizontalLayout row1 = new HorizontalLayout();
        row1.setSizeFull();
        row1.addComponents(cargoNameField, cargoDescriptionField);

        HorizontalLayout row2 = new HorizontalLayout();
        row2.setSizeFull();
        row2.addComponents(cargoWeightField, cargoWidthField, cargoHeightField, cargoDepthField);

        addComponents(row1, row2);

        cargoBinder.forField(cargoNameField).bind(Cargo::getName, Cargo::setName);
        cargoBinder.forField(cargoDescriptionField).bind(Cargo::getDescription, Cargo::setDescription);
    }

    public Binder<Cargo> getBinder(){
        return cargoBinder;
    }

    public Cargo getCargoFromFormData() throws ValidationException {
        Cargo cargo = new Cargo();
        cargoBinder.writeBean(cargo);
        return cargo;
    }

    public void setFormData(Cargo cargo) {
        cargoBinder.readBean(cargo);
    }
}
