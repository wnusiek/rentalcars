package com.example.rentalcars.views.main.manager;

import com.example.rentalcars.service.CrmService;
import com.example.rentalcars.service.DepartmentService;
import com.example.rentalcars.service.RentalService;
import com.example.rentalcars.service.ReturnService;
import com.example.rentalcars.views.main.MainLayout;
import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

import java.util.LinkedHashMap;
import java.util.Map;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard")
@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
@RolesAllowed({"ROLE_MANAGER", "ROLE_ADMIN"})
public class DashboardView extends VerticalLayout {
    private CrmService crmService;
    private DepartmentService departmentService;
    private RentalService rentalService;
    private ReturnService returnService;

    public DashboardView(CrmService crmService, DepartmentService departmentService, RentalService rentalService, ReturnService returnService) {
        this.crmService = crmService;
        this.departmentService = departmentService;
        this.rentalService = rentalService;
        this.returnService = returnService;
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
//                getEmployeesInDepartmentsChart(),
//                getCarsInDepartmentsChart(),
                getPopularReservations()
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

    private ApexCharts getPopularReservations() {
//        Chart chart = new Chart(ChartType.PIE);
        DataSeries dataSeries = new DataSeries();
//        departmentService.getDepartmentList1().forEach(department -> {
//            dataSeries.add(new DataSeriesItem(
//                    department.getCity(),
//                    rentalService.getRentalList().stream()
//                            .filter(rental -> rental.getReservation().getReceptionVenue().equals(department.getCity()))
//                            .count()));
//        });

//        chart.getConfiguration().setSeries(dataSeries);
//        return chart;

//        Grid<DataSeries> grid = new Grid<>(DataSeries.class, true);
//        grid.setSizeFull();
//        grid.setItems(dataSeries);
//        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        Map<String, Long> data = new LinkedHashMap<>();
        for (DataSeriesItem item : dataSeries.getData()){
            data.put(item.getName(), item.getY().longValue());
        }
        ApexCharts pieChart = ApexChartsBuilder.get()
                .withChart(ChartBuilder.get().withType(Type.PIE).build())
                .withSeries(new Series<>("Series 1", data.values().toArray()))
                .withLabels(data.keySet().toArray(new String[0]))
                .build();
        return pieChart;
    }
}
