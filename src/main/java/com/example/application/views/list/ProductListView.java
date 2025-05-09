package com.example.application.views.list;

import com.example.application.data.Product;
import com.example.application.layout.UserView;
import com.example.application.services.ProductService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.List;
import java.util.stream.Collectors;

@AnonymousAllowed
@PageTitle("Products | E-Commerce")
@Route(value = "products", layout = UserView.class)
public class ProductListView extends VerticalLayout {

    private final ProductService productService;
    private final FlexLayout grid;

    private final TextField searchField = new TextField();
    private final ComboBox<String> filterOptions = new ComboBox<>("Browse Products By");


    public ProductListView(ProductService productService) {
        this.productService = productService;

        setPadding(true);
        setSpacing(true);
        setSizeFull();
        addClassName("product-list-view");

        // Search and Filter Bar
        searchField.setPlaceholder("Search...");

        // Unified search and filter
        filterOptions.setItems("Product Name");
        filterOptions.setValue("Product Name");

        Button searchButton = new Button(new Icon(VaadinIcon.SEARCH), event ->
                updateList());
        searchButton.addClassName("text-button");

        HorizontalLayout filters = new HorizontalLayout(
                filterOptions,
                searchField,
                searchButton);
        filters.setWidthFull();
        filters.getStyle().set("padding", "10px");
        filters.setAlignItems(Alignment.END);

        Span subTitle = new Span("CUSTOMER CHOICE");
        subTitle.addClassName("essential-scents");

        H1 title = new H1("Hottest Right Now");
        title.addClassName("title-text");

        VerticalLayout text = new VerticalLayout(subTitle, title);
        text.getStyle().set("align-items", "center");


        add(text, filters);

        // Product Grid
        grid = new FlexLayout();
        grid.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        grid.setWidthFull();
        add(grid);

        updateList();
    }

    private void updateList() {
        grid.removeAll();

        String searchTerm = searchField.getValue();
        String filterType = filterOptions.getValue();

        List<Product> products = productService.findAllProducts(searchTerm).stream()
                .filter(p -> {
                    if(filterType.equals("Company")) {
                        return searchTerm == null ||
                                p.getCompany().getName().toLowerCase().contains(searchTerm.toLowerCase());
                    }  else {
                        return searchTerm == null || p.getName().toLowerCase().contains(searchTerm.toLowerCase());
                    }
                })
                .collect(Collectors.toList());

        products.forEach(product -> grid.add(createProductCard(product)));
    }

    private Div createProductCard(Product product) {

        // Product Card
        Div card = new Div();
        card.addClassName("product-card");
        card.setHeight("450px");
        card.setWidth("270px");

        // Image for product
        Image image = new Image(product.getImageUrl() != null
                ? product.getImageUrl()
                : "https://via.placeholder.com/300"
                , product.getName());

        // Details
        Span details = new Span("Details");
        details.addClassName("details");
        details.addClickListener(spanClickEvent ->
                getUI().ifPresent(ui -> ui.navigate("/product-details/" + product.getId())));


        Div priceDiv = new Div(new Text("$" + product.getPrice()));

        Div productName = new Div(new Text(product.getName()));
        productName.getStyle().set("font-size","20px");

        // Name, price, and button container (covers 25%)
        Div infoContainer = new Div();
        infoContainer.add(
                productName,
                priceDiv,
                details);
        infoContainer.addClassName("info-container");

        card.add(image, infoContainer);

        return card;
    }
}
