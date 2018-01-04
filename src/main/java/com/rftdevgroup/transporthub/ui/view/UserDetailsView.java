package com.rftdevgroup.transporthub.ui.view;

import com.rftdevgroup.transporthub.data.dto.user.AddressDTO;
import com.rftdevgroup.transporthub.data.dto.user.UserDTO;
import com.rftdevgroup.transporthub.data.dto.user.UserUpdateDTO;
import com.rftdevgroup.transporthub.data.model.user.Address;
import com.rftdevgroup.transporthub.data.model.user.User;
import com.rftdevgroup.transporthub.service.UserService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

@SpringView(name = Views.USER_DETAILS)
@Slf4j
public class UserDetailsView extends FormLayout implements View {

    private UserService userService;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;

    private TextField countryField;
    private TextField zipcodeField;
    private TextField cityField;
    private TextField streetField;
    private TextField houseNoField;

    private Button saveBtn;
    private Button resetBtn;

    private Binder<UserUpdateDTO> userBinder;
    private Binder<Address> addressBinder;
    private Optional<UserUpdateDTO> activeUser;
    private Address activeAddress;

    @Autowired
    public UserDetailsView(UserService userService) {
        this.userService = userService;
        init();
    }

    private void init() {
        userBinder = new Binder<>();

        firstNameField = new TextField("First Name");
        firstNameField.setIcon(VaadinIcons.USER);
        addComponent(firstNameField);
        userBinder.forField(firstNameField).bind(UserUpdateDTO::getFirstName, UserUpdateDTO::setFirstName);

        lastNameField = new TextField("Last Name");
        lastNameField.setIcon(VaadinIcons.USER);
        addComponent(lastNameField);
        userBinder.bind(lastNameField, UserUpdateDTO::getLastName, UserUpdateDTO::setLastName);

        emailField = new TextField("Email");
        emailField.setIcon(VaadinIcons.ENVELOPE);
        addComponent(emailField);
        userBinder.bind(emailField, UserUpdateDTO::getEmail, UserUpdateDTO::setEmail);

        activeUser = userService.findAndMapUser(SecurityContextHolder.getContext().getAuthentication().getName(),UserUpdateDTO.class);
        if(!activeUser.isPresent()) { /* Some bad mojo happened */ }
        activeAddress = activeUser.get().getAddress();

        userBinder.readBean(activeUser.get());


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

        addressBinder.readBean(activeUser.get().getAddress());


        saveBtn = new Button("Save", saveBtnListener);
        addComponent(saveBtn);

        resetBtn = new Button("Reset",resetBtnListener);
        addComponent(resetBtn);
    }

    private Button.ClickListener saveBtnListener = new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            try {
                Address modifiedAddress = new Address();
                UserUpdateDTO modifiedUser = new UserUpdateDTO();
                userBinder.writeBean(modifiedUser);
                addressBinder.writeBean(modifiedAddress);
                modifiedAddress.setId(activeAddress.getId());
                modifiedUser.setAddress(modifiedAddress);
                log.debug(modifiedUser.toString());
                Optional<User> user = userService.findAndMapUser(activeUser.get().getUserName(),User.class);
                userService.updateUser(user.get().getId(), modifiedUser);
                Notification.show("Your profile has been updated successfully!");
            } catch (ValidationException e) {
                Notification.show("Something went wrong!");
            }
        }
    };

    private Button.ClickListener resetBtnListener = new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            userBinder.readBean(activeUser.get());
            addressBinder.readBean(activeUser.get().getAddress());
        }
    };

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
