package com.rftdevgroup.transporthub.ui.view.popup;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;

public class UserPopup extends VerticalLayout {

    private Button userUpdate;
    private Button userDelete;
    private final long userId;

    public UserPopup(long id) {
        this.userId = id;
    }

    @PostConstruct
    private void init() {
        userUpdate = new Button("Update");
        userUpdate.setIcon(VaadinIcons.PENCIL);
        userUpdate.addClickListener(updateButtonClickListener);

        userDelete = new Button("Delete");
        userDelete.setIcon(VaadinIcons.TRASH);
        userDelete.addClickListener(deleteButtonClickListener);

        addComponents(userUpdate, userDelete);
    }

    private Button.ClickListener updateButtonClickListener = new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            Notification.show("Updating "+userId);
        }
    };

    private Button.ClickListener deleteButtonClickListener = new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            Notification.show("Deleting "+userId);
        }
    };
}
