package com.rftdevgroup.transporthub.ui.view;

import com.rftdevgroup.transporthub.data.dto.user.UserRegisterDTO;
import com.rftdevgroup.transporthub.service.UserService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

@SpringView
public class RegistrationView extends FormLayout implements View {

    private TextField userNameField;
    private TextField emailField;
    private PasswordField passwordField;
    private PasswordField passwordConfirmationField;
    private UI ui;
    private Binder<UserRegisterDTO> binder;
    private UserRegisterDTO registerDTO;
    private UserService userService;

    public RegistrationView(UserService userService, UI ui) {
        this.userService = userService;
        this.ui = ui;
        this.registerDTO = new UserRegisterDTO();
        this.binder = new Binder<>();
        init();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

    private void init() {
        userNameField = new TextField("Username");
        userNameField.setRequiredIndicatorVisible(true);
        userNameField.setIcon(VaadinIcons.USER);
        binder.forField(userNameField)
                .asRequired("Please provide a username.")
                .withValidator(name -> name.length() >= 3, "Username must be at least 3 characters long.")
                .bind(UserRegisterDTO::getUserName, UserRegisterDTO::setUserName);
        addComponent(userNameField);

        emailField = new TextField("Email address");
        emailField.setIcon(VaadinIcons.ENVELOPE);
        emailField.setRequiredIndicatorVisible(true);
        binder.bind(emailField, UserRegisterDTO::getEmail, UserRegisterDTO::setEmail);
        addComponent(emailField);

        passwordField = new PasswordField("Password");
        passwordField.setRequiredIndicatorVisible(true);
        binder.bind(passwordField, UserRegisterDTO::getPassword, UserRegisterDTO::setPassword);
        addComponent(passwordField);

        Button register = new Button("Register");
        register.setIcon(VaadinIcons.KEY);
        register.addClickListener(registerClickListener);
        addComponent(register);
    }

    private Button.ClickListener registerClickListener = new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            //validate form data
            // .....
            try {
                binder.writeBean(registerDTO);
                userService.regiserUser(registerDTO);
                ui.getPage().setLocation("/transporthub/");
            } catch (ValidationException e) {
                Notification.show("Failed to register. Please check error messages.");
            }

        }
    };

}
