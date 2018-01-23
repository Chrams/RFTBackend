package com.rftdevgroup.transporthub.ui.view.admin;

import com.rftdevgroup.transporthub.data.dto.user.UserDTO;
import com.rftdevgroup.transporthub.service.UserService;
import com.rftdevgroup.transporthub.ui.view.Views;
import com.rftdevgroup.transporthub.ui.window.EditUserWindow;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.util.Optional;

@SpringView(name = Views.USER_LIST)
public class UserListView extends VerticalLayout implements View {

    private Grid<UserDTO> users = new Grid<>();
    private UserService userService;
    private HorizontalLayout buttonBar = new HorizontalLayout();

    private Optional<UserDTO> selectedUser = Optional.empty();

    @Autowired
    public UserListView(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    @PostConstruct
    private void init() {
        //Initialize icon bar
        Button deleteButton = new Button("Delete", VaadinIcons.ERASER);
        deleteButton.addClickListener(deleteClickListener);

        Button editButton = new Button("Edit", VaadinIcons.PENCIL);
        editButton.addClickListener(editClickListener);

        buttonBar.addComponents(editButton, deleteButton);
        buttonBar.setDefaultComponentAlignment(Alignment.MIDDLE_RIGHT);
        addComponent(buttonBar);

        //Initialize user grid
        users.setSizeFull();
        users.setItems(userService.listUsers());
        users.addColumn(UserDTO::getId).setCaption("id");
        users.addColumn(UserDTO::getFirstName).setCaption("First Name");
        users.addColumn(UserDTO::getLastName).setCaption("Last Name");
        users.addColumn(UserDTO::getUserName).setCaption("Username");
        users.addColumn(UserDTO::getEmail).setCaption("Email");
        users.addColumn(UserDTO::getRoles).setCaption("Roles");
        users.addColumn(UserDTO::getAddress).setCaption("Address");
        users.setSelectionMode(Grid.SelectionMode.SINGLE);
        users.addSelectionListener(userGridSelectionEvent);
        addComponent(users);
    }

    private Button.ClickListener editClickListener = new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            if(selectedUser.isPresent()){
                //
                EditUserWindow editWindow = new EditUserWindow(selectedUser.get());
                UI.getCurrent().addWindow(editWindow);
            } else {
                Notification.show("Select a user from the list.");
            }
        }
    };

    private Button.ClickListener deleteClickListener = new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            if(selectedUser.isPresent()){
                //
            } else {
                Notification.show("Select a user from the list.");
            }
        }
    };

    private SelectionListener<UserDTO> userGridSelectionEvent = new SelectionListener<UserDTO>() {
        @Override
        public void selectionChange(SelectionEvent<UserDTO> selectionEvent) {
            selectedUser = selectionEvent.getFirstSelectedItem();
        }
    };
}
