package com.rftdevgroup.transporthub.ui;

import com.rftdevgroup.transporthub.configuration.security.CustomAuthProvider;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringUI(path = "/login")
@Title("Login Page")
@Theme("valo")
public class LoginUI extends UI {

    @Autowired
    private CustomAuthProvider authProvider;

    TextField user;
    PasswordField password;
    Button loginButton = new Button("Login", this::loginBtnClick);

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setSizeFull();

        user = new TextField("User:");
        user.setWidth("300px");

        password = new PasswordField("Password:");
        password.setWidth("300px");
        password.setValue("");

        VerticalLayout fields = new VerticalLayout(user, password, loginButton);
        fields.setCaption("Login form");
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

        VerticalLayout uiLayout = new VerticalLayout(fields);
        uiLayout.setSizeFull();
        uiLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
        setFocusedComponent(user);

        setContent(uiLayout);
    }

    private void loginBtnClick(Button.ClickEvent e) {
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getValue(), password.getValue());
        Authentication authenticated = authProvider.authenticate(auth);
        SecurityContextHolder.getContext().setAuthentication(auth);

        getPage().setLocation("/transporthub/");
    }
}
