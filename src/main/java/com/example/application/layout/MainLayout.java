package com.example.application.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        getElement().getStyle().set("background", "transparent");
    }

    private void createHeader() {
        VerticalLayout header = new VerticalLayout();
        header.addClassName("header");
        header.setPadding(true);
        header.setWidthFull();
        header.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        // Center logo
        H1 logo = new H1(" SCENTED CANDLES");
        logo.addClassName("logo");
        logo.getStyle().set("text-align", "center");

        header.add(logo);

        addToNavbar(header);
    }
}