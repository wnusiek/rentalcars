package com.example.rentalcars.views.main;

import com.example.rentalcars.model.CompanyModel;
import com.example.rentalcars.service.CompanyService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "configuration", layout = MainLayout.class)
@PageTitle("Company configuration")
public class CompanyView extends VerticalLayout {

    private final CompanyService companyService;
    Grid<CompanyModel> grid = new Grid<>(CompanyModel.class);

    public CompanyView(CompanyService companyService) {
        this.companyService = companyService;
        addClassName("company-view");
        setSizeFull();
        configureGrid();
        add(grid);
        getCompanyInfo();
    }
    private void getCompanyInfo() {
        grid.setItems(companyService.getCompanyModelList());
    }
    private void configureGrid() {
        grid.addClassNames("company-grid");
        grid.setSizeFull();
        grid.setColumns("companyName", "companyDomainURL", "companyAddress", "companyOwner", "companyLogotype");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

}
