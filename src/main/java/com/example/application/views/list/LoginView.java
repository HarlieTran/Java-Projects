package com.example.application.views.list;

import com.example.application.security.SecurityService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Login | E-commerce")
@Route("login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm loginForm = new LoginForm();

    public LoginView(SecurityService securityService) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        addClassName("login-view");

        H1 title = new H1("It's nice to see you again!");
        title.addClassName("title-text");

        // Ensure the login form submits to the correct Spring Security endpoint
        loginForm.setAction("login");
        loginForm.setForgotPasswordButtonVisible(false);
        loginForm.addClassName("custom-login-form");

        // Back to WelcomeView button
        Button backButton = new Button("Cancel", e ->
                UI.getCurrent().navigate(""));
        backButton.addClassName("back-button");

        // Login Card
        VerticalLayout card = new VerticalLayout();
        card.setWidth("400px");
        card.setAlignItems(Alignment.CENTER);
        card.addClassName("login-card");
        card.add(loginForm, backButton);

        add(title, card);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // Show error message if login fails
        if (beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            loginForm.setError(true);
        }
    }
}