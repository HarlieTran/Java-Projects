package com.example.application.layout;


import com.example.application.security.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "user")
@PageTitle("E-Commerce")
@AnonymousAllowed
public class UserView extends AppLayout {

    private final SecurityService securityService;

    public UserView(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        addClassName("default-view");
    }

    private void createHeader() {
        // Title
        H1 title = new H1("SCENTED CANDLES");
        title.addClassName("logo");
        title.getStyle().set("text-align", "left");
        title.addClassNames("text-1","m-m");

        // Cart Button
        Button cartButton = new Button(new Icon(VaadinIcon.CART));
        cartButton.addClassName("text-button");

        HorizontalLayout userLogout = new HorizontalLayout();
        userLogout.setSpacing(true);
        userLogout.setPadding(true);
        userLogout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Display the logged-in username
        if (securityService.getAuthenticatedUser() != null) {
            String username = securityService.getAuthenticatedUser().getUsername();
            Span usernameField = new Span("Hello, " + username);

            Button logoutButton = new Button("Logout", e ->
                    securityService.logout());
            logoutButton.addClassName("text-button");

            cartButton.addClickListener(event -> {
                getUI().ifPresent(ui -> ui.navigate("cart"));
            });

            userLogout.add(usernameField, logoutButton, cartButton);

        } else {
            Button loginButton = new Button("Login", e ->
                    getUI().ifPresent(ui -> ui.navigate("login")));
            loginButton.addClassName("text-button");

            cartButton.addClickListener(event -> {
                getUI().ifPresent(ui -> ui.navigate("login"));
            });

            userLogout.add(loginButton, cartButton);

        }

        // Header Layout
        HorizontalLayout header = new HorizontalLayout(title, userLogout);
        header.setWidthFull();
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.addClassNames("py-0", "px-m");

        // Expand title to push userLogout to the right
        header.expand(title);

        addToNavbar(header);

    }
}