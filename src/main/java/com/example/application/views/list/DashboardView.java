package com.example.application.views.list;


import com.example.application.layout.AdminView;
import com.example.application.services.ProductService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@RolesAllowed("ADMIN")
@Route(value = "dashboard", layout = AdminView.class)
@PageTitle("Dashboard | E-commerce")
public class DashboardView extends VerticalLayout {
    private final ProductService productService;

    public DashboardView(ProductService productService) {
        this.productService = productService;
        addClassName("list-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getContactStats(), getCompaniesChart());
    }

    private Component getContactStats() {
        Span stats = new Span(productService.countProducts() + " products");
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    private Chart getCompaniesChart() {
        Chart chart = new Chart(ChartType.COLUMN);

        DataSeries dataSeries = new DataSeries();
        productService.findAllCompanies().forEach(company ->
                dataSeries.add(new DataSeriesItem(company.getName(), company.getProductCount())));
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }
}