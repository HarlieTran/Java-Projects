package com.example.application.layout;


import com.example.application.security.SecurityService;
import com.example.application.views.list.AccountView;
import com.example.application.views.list.DashboardView;
import com.example.application.views.list.ListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "admin")
@PageTitle("Administration")
@RolesAllowed("ADMIN")
public class AdminView extends AppLayout {

    private final SecurityService securityService;

    public AdminView(SecurityService securityService) {
        this.securityService = securityService;
        addClassName("default-view");
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        // Title
        H1 title = new H1("SCENTED CANDLES");
        title.addClassName("logo");

        // Drawer toggle at the top-left
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName("text-button");

        // Title and toggle
        HorizontalLayout toggleAndTitle = new HorizontalLayout(toggle, title);
        toggleAndTitle.setAlignItems(FlexComponent.Alignment.START);

        HorizontalLayout userLogout = new HorizontalLayout();
        userLogout.setPadding(true);

        // Display the logged-in username
        if (securityService.getAuthenticatedUser() != null) {

            String username = securityService.getAuthenticatedUser().getUsername();
            Span usernameField = new Span("Hello, " + username);

            Button logoutButton = new Button("Logout", e ->
                    securityService.logout());
            logoutButton.addClassName("text-button");

            userLogout.add(usernameField, logoutButton);
            userLogout.setAlignItems(FlexComponent.Alignment.CENTER);

        }

        userLogout.getStyle().set("margin-left", "auto");

        HorizontalLayout header = new HorizontalLayout(toggleAndTitle, userLogout);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();

        addToNavbar(header);
    }

    private void createDrawer() {

        RouterLink listLink = new RouterLink("Product Management", ListView.class);
        listLink.addClassNames("link");

        RouterLink dashboardLink = new RouterLink("Dashboard", DashboardView.class);
        dashboardLink.addClassNames("link");

        RouterLink accountLink = new RouterLink("Account", AccountView.class);
        accountLink.addClassNames("link");

        VerticalLayout drawerContent = new VerticalLayout(
                listLink,
                accountLink,
                dashboardLink
        );
        drawerContent.addClassName("custom-drawer");
        // Ensure no internal padding or margin
        drawerContent.setPadding(false);
        drawerContent.setMargin(false);

        addToDrawer(drawerContent);

    }
}