package com.rftdevgroup.transporthub.ui.window;

import com.rftdevgroup.transporthub.data.model.user.Address;
import com.rftdevgroup.transporthub.data.model.user.User;
import com.rftdevgroup.transporthub.data.model.user.UserDetails;
import com.rftdevgroup.transporthub.service.UserService;
import com.rftdevgroup.transporthub.ui.components.AddressForm;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EditUserWindow extends Window {

    private VerticalLayout layout = new VerticalLayout();
    private User userToEdit;
    private UserService userService;

    private TextField userNameField = new TextField("Username");
    private TextField firstNameField = new TextField("First Name");
    private TextField lastNameField = new TextField("Last Name");
    private TextField emailField = new TextField("Email");

    private Binder<UserDetails> detailsBinder = new Binder<>();
    private Binder<Address> addressBinder;

    public EditUserWindow(UserService userService, long id) {
        super("Edit User Form");
        this.userService = userService;
        userToEdit = userService.findById(id);
        center();
        init();
        setContent(layout);
    }

    private void init() {

        userNameField.setReadOnly(true);
        userNameField.setValue(userToEdit.getUserName());
        layout.addComponent(userNameField);

        UserDetails details = userToEdit.getDetails();

        HorizontalLayout detailsRow = new HorizontalLayout();
        detailsRow.addComponents(firstNameField, lastNameField, emailField);
        detailsBinder.forField(firstNameField)
                .withValidator(firstName -> firstName.length() > 0, "First name cannot be empty")
                .bind(UserDetails::getFirstName, UserDetails::setFirstName);
        detailsBinder.forField(lastNameField)
                .withValidator(lastName -> lastName.length() > 0, "Last name cannot be empty")
                .bind(UserDetails::getLastName, UserDetails::setLastName);
        detailsBinder.forField(emailField)
                .withValidator(email -> email.length() > 3, "Please provide a valid email")
                .bind(UserDetails::getEmail, UserDetails::setEmail);
        layout.addComponent(detailsRow);

        AddressForm addressForm = new AddressForm();
        layout.addComponent(addressForm);
        addressBinder = addressForm.getBinder();

        Button saveBtn = new Button("Save");
        saveBtn.addClickListener(saveBtnClick);
        layout.addComponent(saveBtn);

        detailsBinder.readBean(userToEdit.getDetails());
        addressBinder.readBean(userToEdit.getDetails().getAddress());

        layout.setMargin(true);
    }

    private Button.ClickListener saveBtnClick = new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            Notification.show("Saving");
            UserDetails details = userToEdit.getDetails();
            Address address = details.getAddress();
            try {
                detailsBinder.writeBean(details);
                addressBinder.writeBean(address);
                details.setAddress(address);
                userToEdit.setDetails(details);
                log.debug("User to save: {}", userToEdit);
                log.debug("Details to save: {}", userToEdit.getDetails());
                log.debug("Address to save: {}", userToEdit.getDetails().getAddress());

                User savedUser = userService.save(userToEdit);

                log.debug("saved user: {}", savedUser);
                log.debug("saved details: {}", savedUser.getDetails());
                log.debug("saved address: {}", savedUser.getDetails().getAddress());
                UI.getCurrent().getPage().reload();
                close();

            } catch (ValidationException e) {
                e.printStackTrace();
            }
        }
    };
}
