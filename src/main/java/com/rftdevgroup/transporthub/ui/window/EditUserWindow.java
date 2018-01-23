package com.rftdevgroup.transporthub.ui.window;

import com.rftdevgroup.transporthub.data.dto.user.UserDTO;
import com.rftdevgroup.transporthub.data.dto.user.UserUpdateDTO;
import com.vaadin.data.Binder;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.PostConstruct;

@Slf4j
public class EditUserWindow extends Window {

    private FormLayout layout = new FormLayout();
    private UserDTO userToEdit;
    private Binder<UserUpdateDTO> binder = new Binder<>();
    private UserUpdateDTO updateDTO = new UserUpdateDTO();

    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");
    private TextField email = new TextField("Email Address");

    public EditUserWindow(UserDTO userToEdit){
        super("Edit User Form");
        this.userToEdit = userToEdit;
        center();
        init();
        setContent(layout);
    }

    private  void init(){
        layout.addComponent(firstName);
        binder.forField(firstName).bind(UserUpdateDTO::getFirstName, UserUpdateDTO::setFirstName);
        layout.addComponent(lastName);
        binder.forField(lastName).bind(UserUpdateDTO::getLastName, UserUpdateDTO::setLastName);
        layout.addComponent(email);
        binder.forField(email).bind(UserUpdateDTO::getEmail, UserUpdateDTO::setEmail);

        setUpdateDTO();
        binder.readBean(updateDTO);

        layout.setMargin(true);
    }

    private void setUpdateDTO(){
        updateDTO.setAddress(userToEdit.getAddress());
        updateDTO.setEmail(userToEdit.getEmail());
        updateDTO.setFirstName(userToEdit.getFirstName());
        updateDTO.setLastName(userToEdit.getLastName());
        binder.readBean(updateDTO);
    }
}
