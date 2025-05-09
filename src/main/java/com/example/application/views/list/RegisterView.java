package com.example.application.views.list;

import com.example.application.entity.User;
import com.example.application.repository.UserRepository;
import com.example.application.security.SecurityService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@PageTitle("Register | E-commerce")
@Route("register")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {


    public RegisterView(UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        SecurityService securityService) {

        addClassName("register-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("Join with Us");
        title.addClassName("title-text");

        H2 reg = new H2("Register");
        reg.addClassName("regis-text");

        // Form fields
        TextField name = new TextField("Name");
        EmailField email = new EmailField("Email");
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");

        // Role selection (only visible to admins)
        ComboBox<String> roleComboBox = new ComboBox<>("Role");
        roleComboBox.setItems(List.of("USER", "ADMIN"));
        roleComboBox.setValue("USER"); // Default role
        roleComboBox.setVisible(false); // Hidden by default

        // Check if the curren user is admin
        if (securityService.getAuthenticatedUser() != null
            && securityService.getAuthenticatedUser()
                .getAuthorities()
                .stream()
                .anyMatch(auth ->
                        auth.getAuthority().equals("ROLE_ADMIN"))) {
            roleComboBox.setVisible(true);
        }

        // Register button

        Button registerButton = new Button("Register", event -> {
            User user = new User();
            user.setName(name.getValue());
            user.setEmail(email.getValue());
            user.setUsername(username.getValue());
            user.setPassword(passwordEncoder.encode(password.getValue()));
            user.setRole(roleComboBox.getValue());
            userRepository.save(user);
            Notification.show("Registered Successfully");
            getUI().ifPresent(ui -> ui.navigate(LoginView.class));
        });
        registerButton.addClassName("login-button");

        // Back Button
        Button cancelButton = new Button("Cancel", event -> {
            UI.getCurrent().getPage().executeJs("window.history.back()");
        });
        cancelButton.addClassName("login-button");

        HorizontalLayout regButtons = new HorizontalLayout(registerButton, cancelButton);

        VerticalLayout registerLayout = new VerticalLayout(reg, name, email, username, password, roleComboBox, regButtons);
        registerLayout.addClassName("register-layout");
        registerLayout.setWidth("500px");
        registerLayout.setAlignItems(Alignment.CENTER);

        //FormLayout formLayout = new FormLayout(title, username, password, registerButton);
        add(title, registerLayout);

    }
}