package com.example.application.views.list;

import com.example.application.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Welcome | E-commerce")
@Route("")
@AnonymousAllowed
public class WelcomeView extends VerticalLayout {

    public WelcomeView() {
        addClassName("landing-page");
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setAlignItems(Alignment.CENTER);
        getStyle().set("margin", "0");
        getStyle().set("padding", "0");

        // Navigation bar
        MainLayout nabar = new MainLayout();
        add(nabar);

        // Hero section with full viewport background
        createHeroSection();
    }

    private void createHeroSection() {

        // Content overlay
        Div contentOverlay = new Div();
        contentOverlay.addClassName("content-overlay");

        Span essentialScents = new Span("ESSENTIAL SCENTS");
        essentialScents.addClassName("essential-scents");

        H1 aromatherapy = new H1("AROMATHERAPY");
        aromatherapy.addClassName("title-text");

        H1 delights = new H1("DELIGHTS");
        delights.addClassName("title-text");

        // Stylish "The Mood" text
        Span theMood = new Span("A fragrance is a chemical mixture that has a smell or odor " +
                "- but it is so much more besides, encompassing cultural, historical, " +
                "social, economic and emotional value.");
        theMood.addClassName("cursive-text");

        // Button
        HorizontalLayout buttonsLayout = new HorizontalLayout();

        Button loginButton = new Button("LOGIN", e ->
                getUI().ifPresent(ui -> ui.navigate("/login")));
        loginButton.addClassName("login-button");

        Button registerButton = new Button("REGISTER", e ->
                getUI().ifPresent(ui -> ui.navigate("/register")));
        registerButton.addClassName("register-button");

        Button continueButton = new Button("CONTINUE AS A GUEST", e ->
                getUI().ifPresent(ui -> ui.navigate("/products")));
        continueButton.addClassName("continue-button");

        buttonsLayout.add(loginButton, registerButton, continueButton);

        contentOverlay.add(essentialScents, aromatherapy, delights, theMood, buttonsLayout);

        add(contentOverlay);
    }
}