package com.example.application.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Allow access to the WelcomeView and ProductListView without authentication
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/").permitAll()   //Welcome page
                .requestMatchers("login").permitAll()  //Login page
                .requestMatchers("products").permitAll()   //ProductListView
                .requestMatchers("product-details/**").permitAll()  //Allow ProductDetailsView for guest
                .requestMatchers("cart").authenticated()   // Require login for cart
                .requestMatchers("product-management").hasRole("ADMIN")    //Admin only page
                .requestMatchers("dashboard").hasRole("ADMIN")     //Admin only page
                .requestMatchers("META-INF/resources/**").permitAll() // Allow access to static resources
                .requestMatchers("images/**", "icons/**").permitAll() // Allow access to static resources
                .requestMatchers("account").hasRole("ADMIN") // Allow only admin to view account list
        );

        // Call Vaadin's default security configuration
        super.configure(http);

        // Custom success handler to redirect based on role
       http.formLogin(c -> c.successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                // Determine the target URL based on the user's role
                if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                    response.sendRedirect("/product-management");
                //} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
                    //response.sendRedirect("/products");
                } else {
                    response.sendRedirect("/products");
                }
            }
        }));
    }

    //@Bean
    /*public InMemoryUserDetailsManager userDetailsService() {
        // Hard-code user credentials
        UserDetails user = User.withUsername("user")
                .password("{noop}userpass") // {noop} means plain text password (for demo purposes)
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password("{noop}adminpass")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
       /* http.formLogin(c -> c.successHandler((request, response, authentication) -> {
            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                response.sendRedirect("/product-management");
            } else {
                response.sendRedirect("/products");
            }
        }));
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}