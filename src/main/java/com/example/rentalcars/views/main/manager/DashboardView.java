package com.example.rentalcars.views.main.manager;

import com.example.rentalcars.service.CrmService;
import com.example.rentalcars.service.DepartmentService;
import com.example.rentalcars.views.main.MainLayout;
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
import org.springframework.security.access.annotation.Secured;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard")
@Secured("ROLE_ADMIN")
@RolesAllowed("ROLE_ADMIN")
public class DashboardView extends VerticalLayout {
    private CrmService crmService;
    private DepartmentService departmentService;

    public DashboardView(CrmService crmService, DepartmentService departmentService) {
        this.crmService = crmService;
        this.departmentService = departmentService;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(
                getCarStats(),
                getEmployeesStats(),
                getCustomersStats(),
                getReservationsStats(),
                getRentalsStats(),
                getReturnsStats(),
                getDepartmentsStats(),
                getEmployeesInDepartmentsChart(),
                getCarsInDepartmentsChart()
        );
    }

    private Component getCarStats() {
        Span stats = new Span("Liczba samochodów: " + crmService.countCars());
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    private Component getEmployeesStats() {
        Span stats = new Span("Liczba pracowników: " + crmService.countEmployees());
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    private Component getCustomersStats() {
        Span stats = new Span("Liczba klientów: " + crmService.countCustomers());
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    private Component getReservationsStats() {
        Span stats = new Span("Liczba rezerwacji: " + crmService.countReservations());
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    private Component getRentalsStats() {
        Span stats = new Span("Liczba wypożyczeń: " + crmService.countRentals());
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    private Component getReturnsStats() {
        Span stats = new Span("Liczba zwrotów: " + crmService.countReturns());
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    private Component getDepartmentsStats() {
        Span stats = new Span("Liczba oddziałów: " + crmService.countDepartments());
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }


    private Component getEmployeesInDepartmentsChart(){
        Chart chart = new Chart(ChartType.PIE);
        DataSeries dataSeries = new DataSeries();
        departmentService.getDepartmentList1().forEach(department -> {
            dataSeries.add(new DataSeriesItem(department.getCity(), department.getEmployees().stream().count()));
        });
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }

    private Component getCarsInDepartmentsChart() {
        Chart chart = new Chart(ChartType.PIE);
        DataSeries dataSeries = new DataSeries();
        departmentService.getDepartmentList1().forEach(department -> {
            dataSeries.add(new DataSeriesItem(department.getCity(), department.getCars().stream().count()));
        });
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }
}
