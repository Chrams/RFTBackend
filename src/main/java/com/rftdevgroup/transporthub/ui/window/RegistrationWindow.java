package com.rftdevgroup.transporthub.ui.window;

import com.rftdevgroup.transporthub.service.UserService;
import com.vaadin.ui.Window;

public class RegistrationWindow extends Window {

    private UserService userService;


    public RegistrationWindow(UserService userService){
        super("Registration Form");
        this.userService = userService;
        center();
        init();
    }

    private void init(){

    }
}
