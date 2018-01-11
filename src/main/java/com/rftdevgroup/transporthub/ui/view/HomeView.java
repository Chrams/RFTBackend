package com.rftdevgroup.transporthub.ui.view;

import com.rftdevgroup.transporthub.ui.view.menu.MainMenu;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

@SpringView(name = Views.HOME)
public class HomeView extends VerticalLayout implements View {

    private Label label = new Label();

    private VerticalLayout popupContent = new VerticalLayout();

    public HomeView() {
        label.setCaption("Welcome from the home view.");
        addComponent(label);
        setSizeFull();

        popupContent.addComponent(new TextField("Textfield"));
        popupContent.addComponent(new Button("Button"));

        PopupView popup = new PopupView(null, popupContent);


        Button button = new Button("Show details", click -> popup.setPopupVisible(true));

        addComponent(button);

        addComponent(popup);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
