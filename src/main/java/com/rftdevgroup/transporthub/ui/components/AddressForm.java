package com.rftdevgroup.transporthub.ui.components;

import com.rftdevgroup.transporthub.data.model.user.Address;
import com.vaadin.data.Binder;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.PostConstruct;

@Slf4j
public class AddressForm extends FormLayout {

    private TextField countryField;
    private TextField zipcodeField;
    private TextField cityField;
    private TextField streetField;
    private TextField houseNoField;

    private Binder<Address> addressBinder;

    public AddressForm(){
        init();
    }

    private void init() {
        log.debug("AddressForm initialization");
        addressBinder = new Binder<>();

        countryField = new TextField("Country");
        addComponent(countryField);
        addressBinder.bind(countryField, Address::getCountry, Address::setCountry);

        cityField = new TextField("City");
        addComponent(cityField);
        addressBinder.bind(cityField, Address::getCity, Address::setCity);

        streetField = new TextField("Street");
        addComponent(streetField);
        addressBinder.bind(streetField, Address::getStreet, Address::setStreet);

        houseNoField = new TextField("House No");
        addComponent(houseNoField);
        addressBinder.bind(houseNoField, Address::getHouseNo, Address::setHouseNo);

        zipcodeField = new TextField("ZipCode");
        addComponent(zipcodeField);
        addressBinder.bind(zipcodeField, Address::getZipcode, Address::setZipcode);
    }

    public Binder<Address> getBinder() {
        return addressBinder;
    }

}
