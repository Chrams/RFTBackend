package com.rftdevgroup.transporthub.ui.view;

import com.rftdevgroup.transporthub.data.dto.transport.TransportDetailsDTO;
import com.rftdevgroup.transporthub.data.dto.transport.TransportListViewDTO;
import com.rftdevgroup.transporthub.data.model.user.User;
import com.rftdevgroup.transporthub.data.repository.transport.TransportRepository;
import com.rftdevgroup.transporthub.data.repository.user.UserRepository;
import com.rftdevgroup.transporthub.service.TransportService;
import com.rftdevgroup.transporthub.service.UserService;
import com.rftdevgroup.transporthub.ui.window.NewTransportWindow;
import com.rftdevgroup.transporthub.ui.window.TransportDetailsWindow;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.annotation.PostConstruct;
import java.util.Optional;

@SpringView(name = Views.TRANSPORT_LIST)
public class TransportView extends VerticalLayout implements View {

    private TransportService transportService;
    private UserService userService;

    private Grid<TransportListViewDTO> transportGrid = new Grid<>(TransportListViewDTO.class);
    private Grid<TransportListViewDTO> ownTransportGrid = new Grid<>(TransportListViewDTO.class);

    private Optional<TransportListViewDTO> selected = Optional.empty();
    private Optional<TransportListViewDTO> selectedOwn = Optional.empty();

    private Authentication auth;

    @Autowired
    public TransportView(TransportService transportService, UserService userService) {
        this.transportService = transportService;
        this.userService = userService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    @PostConstruct
    private void init() {
        this.auth = SecurityContextHolder.getContext().getAuthentication();
        HorizontalLayout buttons = new HorizontalLayout();
        Button newTransport = new Button("New Transport", VaadinIcons.PLUS);
        newTransport.addClickListener(newTransportClickListener);

        for(Object o : auth.getAuthorities())
        {
            if(o.toString().compareTo("ROLE_ADMIN") == 0)
            {
                Button deleteButton = new Button("Delete",VaadinIcons.TRASH);
                deleteButton.addClickListener(clickEvent -> {
                    if(selected.isPresent()) transportService.adminDelete(selected.get().getId());
                });
            }
        }

        buttons.addComponent(newTransport);
        addComponent(buttons);

        transportGrid.addItemClickListener(gridItemClickListener);
        transportGrid.setSizeFull();
        transportGrid.setColumns("id", "cargoName", "cityFrom", "cityTo", "description", "daysRemaining", "owner", "currentPrice", "lowestBidder");

        transportGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        transportGrid.addSelectionListener(selectionEvent -> {
            selected = selectionEvent.getFirstSelectedItem();
        });
        addComponent(transportGrid);


        HorizontalLayout ownButtons = new HorizontalLayout();
        Button deleteMyTransport = new Button("Delete",VaadinIcons.TRASH);
        deleteMyTransport.addClickListener(deleteClickListener);
        ownButtons.addComponent(deleteMyTransport);
        addComponent(ownButtons);

        ownTransportGrid.setSizeFull();
        ownTransportGrid.setColumns("id", "cargoName", "cityFrom", "cityTo", "description", "daysRemaining", "currentPrice", "lowestBidder");
        ownTransportGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        addComponent(new Label("My transports"));
        ownTransportGrid.addSelectionListener(selectionEvent -> {
            selectedOwn = selectionEvent.getFirstSelectedItem();
        });
        addComponent(ownTransportGrid);

        if (transportService == null) {
            System.err.println("TRANSPORT SERVICE IS NULL!!");
        } else {
            transportGrid.setItems(transportService.listTransports());
            Optional<User> self = userService.findAndMapUser(SecurityContextHolder.getContext().getAuthentication().getName(),User.class);
            if(self.isPresent())
            {
                ownTransportGrid.setItems(transportService.listUsersTransports(self.get()));
            }

        }
    }

    private ItemClickListener<TransportListViewDTO> gridItemClickListener = new ItemClickListener<TransportListViewDTO>() {
        @Override
        public void itemClick(Grid.ItemClick<TransportListViewDTO> itemClick) {
            if (itemClick.getMouseEventDetails().isDoubleClick()) {
                TransportListViewDTO selected = itemClick.getItem();
                Optional<TransportDetailsDTO> detailsDTO = transportService.findAndMapTransport(selected.getId(), TransportDetailsDTO.class);
                if (detailsDTO.isPresent()) {
                    TransportDetailsWindow detailsWindow = new TransportDetailsWindow(detailsDTO.get());
                    UI.getCurrent().addWindow(detailsWindow);
                } else {
                    Notification.show("Cannot find selected transport job.");
                }
            }
        }
    };

    private Button.ClickListener newTransportClickListener = new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            Optional<User> owner = userService.findAndMapUser(SecurityContextHolder.getContext().getAuthentication().getName(),User.class);
            if (owner.isPresent()) {
                NewTransportWindow newTransportWindow = new NewTransportWindow(transportService, owner.get());
                UI.getCurrent().addWindow(newTransportWindow);
            }
        }
    };

    private Button.ClickListener deleteClickListener = new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            if(selectedOwn.isPresent()){
                transportService.delete(selected.get().getId(), userService.findAndMapUser(auth.getName(),User.class).get());
                UI.getCurrent().getPage().reload();
            }
        }
    };
}
