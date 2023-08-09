package com.example.rentalcars.views.main;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@Route(value = "", layout = MainLayout.class)
@PageTitle("Strona główna")
@CssImport(value = "./styles/styles.css")
@AnonymousAllowed
public class HomeView extends VerticalLayout {

    public HomeView(){
        setClassName("home-view");
    }

}


