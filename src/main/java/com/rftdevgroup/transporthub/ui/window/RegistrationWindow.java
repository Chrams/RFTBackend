package com.rftdevgroup.transporthub.ui.window;

import com.rftdevgroup.transporthub.data.dto.user.UserRegisterDTO;
import com.rftdevgroup.transporthub.service.UserService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegistrationWindow extends Window {

    private UserService userService;

    private FormLayout layout = new FormLayout();
    private UserRegisterDTO registerDTO = new UserRegisterDTO();
    private Binder<UserRegisterDTO> registerDTOBinder = new Binder<>();

    private TextField userNameField = new TextField("Username");
    private TextField emailField = new TextField("Email address");
    private PasswordField passwordField = new PasswordField("Password");

    public RegistrationWindow(UserService userService) {
        super("Registration Form");
        this.userService = userService;
        center();
        init();
        setContent(layout);
    }

    private void init() {
        userNameField.setRequiredIndicatorVisible(true);
        userNameField.setIcon(VaadinIcons.USER);
        registerDTOBinder.forField(userNameField)
                .asRequired("Please provide a username.")
                .withValidator(name -> name.length() >= 4, "Username must be at least 4 characters long.")
                .bind(UserRegisterDTO::getUserName, UserRegisterDTO::setUserName);

        layout.addComponent(userNameField);

        emailField.setIcon(VaadinIcons.ENVELOPE);
        emailField.setRequiredIndicatorVisible(true);
        registerDTOBinder.forField(emailField)
                .asRequired("Please provide a valid email address.")
                .withValidator(new EmailValidator("Must be a valid email address."))
                .bind(UserRegisterDTO::getEmail, UserRegisterDTO::setEmail);

        layout.addComponent(emailField);

        passwordField.setIcon(VaadinIcons.LOCK);
        passwordField.setRequiredIndicatorVisible(true);
        registerDTOBinder.forField(passwordField)
                .asRequired("Please provide a password")
                .bind(UserRegisterDTO::getPassword, UserRegisterDTO::setPassword);

        layout.addComponent(passwordField);

        Button register = new Button("Register");
        register.setIcon(VaadinIcons.PLUS);
        register.addClickListener(registerBtnClickListener);

        layout.addComponent(register);

        layout.setMargin(true);
    }

    private Button.ClickListener registerBtnClickListener = new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            try {
                registerDTOBinder.writeBean(registerDTO);
                log.debug(registerDTO.toString());
                userService.regiserUser(registerDTO);
                Notification.show("Registered successfully!");
                close();
            } catch (ValidationException e) {
                Notification.show("Failed to register. Please check error messages.");
            }
        }
    };
}
