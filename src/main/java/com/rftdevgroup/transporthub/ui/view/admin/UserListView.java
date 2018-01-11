package com.rftdevgroup.transporthub.ui.view.admin;

import com.rftdevgroup.transporthub.data.dto.user.UserDTO;
import com.rftdevgroup.transporthub.service.UserService;
import com.rftdevgroup.transporthub.ui.view.Views;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = Views.USER_LIST)
public class UserListView extends VerticalLayout implements View{

    private Grid<UserDTO> users = new Grid<>();
    private UserService userService;

    @Autowired
    public UserListView(UserService userService)
    {
        this.userService = userService;
        init();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    private void init()
    {
        users.setSizeFull();
        users.setItems(userService.listUsers());
        users.addColumn(UserDTO::getId).setCaption("id");
        users.addColumn(UserDTO::getFirstName).setCaption("First Name");
        users.addColumn(UserDTO::getLastName).setCaption("Last Name");
        users.addColumn(UserDTO::getUserName).setCaption("Username");
        users.addColumn(UserDTO::getEmail).setCaption("Email");
        users.addColumn(UserDTO::getRoles).setCaption("Roles");
        users.addColumn(UserDTO::getAddress).setCaption("Address");
        addComponent(users);
    }
}
