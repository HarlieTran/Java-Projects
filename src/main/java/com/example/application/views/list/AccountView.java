package com.example.application.views.list;

import com.example.application.entity.User;
import com.example.application.layout.AdminView;
import com.example.application.repository.UserRepository;
import com.example.application.security.SecurityService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Accounts | E-commerce")
@Route(value = "account", layout = AdminView.class)
@RolesAllowed("ADMIN")
public class AccountView extends VerticalLayout {

    private UserRepository userRepository;
    private SecurityService securityService;

    public AccountView(UserRepository userRepository,
                       SecurityService securityService) {
        this.userRepository = userRepository;
        this.securityService = securityService;
        setSizeFull();
        addClassName("list-view");
        createAccountList();
    }

    private void createAccountList() {
        setSizeFull();

        Button addAccountButton = new Button("Add Account");
        addAccountButton.addClassName("text-button");
        addAccountButton.addClickListener(e ->
                getUI().ifPresent(ui -> ui.navigate("register")));

        // Check if the current user is an admin
        if (securityService.getAuthenticatedUser() != null
                && securityService.getAuthenticatedUser().getAuthorities().stream()
                .anyMatch(a ->
                        a.getAuthority().equals("ROLE_ADMIN"))) {

            // Create a grid to display user accounts
            Grid<User> userGrid = new Grid<>(User.class);
            userGrid.setColumns("name", "email", "username", "role");
            userGrid.setItems(userRepository.findAll()); // Fetch all users from the database

            add(addAccountButton, userGrid);
        }
    }
}
