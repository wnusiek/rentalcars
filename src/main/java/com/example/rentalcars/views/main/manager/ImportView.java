package com.example.rentalcars.views.main.manager;

import com.example.rentalcars.enums.BodyType;
import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.enums.FuelType;
import com.example.rentalcars.enums.GearboxType;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.service.CarService;
import com.example.rentalcars.views.main.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.security.access.annotation.Secured;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Route(value = "importView", layout = MainLayout.class)
@PageTitle("Import samochodów")
@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
@RolesAllowed({"ROLE_MANAGER", "ROLE_ADMIN"})
public class ImportView extends VerticalLayout {
    private final CarService carService;
    public ImportView(CarService carService) {
        this.carService = carService;

        FileBuffer buffer = new FileBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes(".csv");
        upload.addSucceededListener(event -> {
            try {
                List<CarModel> cars = processCSVFile(buffer.getInputStream());
                saveToDatabase(cars);
                Notification.show("Import zakończony pomyślnie").setPosition(Notification.Position.MIDDLE);
            } catch (IOException e){
                e.printStackTrace();
                Notification.show("Błąd podczas importowania danych: " + e.getMessage()).setPosition(Notification.Position.MIDDLE);
            }
        });
        upload.addFileRejectedListener(event -> Notification.show("File rejected").setPosition(Notification.Position.MIDDLE));
        upload.addFailedListener(event -> Notification.show("Upload failed").setPosition(Notification.Position.MIDDLE));
        Button importButton = new Button("Importuj plik CSV");
        importButton.addClickListener(e -> upload.getElement().executeJs("this.click()"));
        add(upload, importButton);
    }


    public void saveToDatabase(List<CarModel> cars) {
        for (CarModel car : cars){
            carService.saveCar(car);
        }
    }

    private List<CarModel> processCSVFile(InputStream inputStream) throws IOException {
        List<CarModel> cars = new ArrayList<>();
        try (Reader reader = new InputStreamReader(inputStream);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())){
            for (CSVRecord csvRecord : csvParser) {
                Long id = 0l;
                String mark = csvRecord.get("Marka");
                String model = csvRecord.get("Model");
                BigDecimal price = BigDecimal.valueOf(Float.parseFloat(csvRecord.get("Cena")));
                BigDecimal bail = BigDecimal.valueOf(Float.parseFloat(csvRecord.get("Kaucja")));
                BodyType body = BodyType.valueOf(csvRecord.get("Typ nadwozia"));
                GearboxType gearbox = GearboxType.valueOf(csvRecord.get("Rodzaj skrzyni biegow"));
                Integer numberOfSeats = Integer.valueOf(csvRecord.get("Liczba miejsc"));
                Integer numberOfDoors = Integer.valueOf(csvRecord.get("Liczba drzwi"));
                FuelType fuelType = FuelType.valueOf(csvRecord.get("Rodzaj paliwa"));
                String trunk = csvRecord.get("Bagaznik");
                CarStatus availability = CarStatus.valueOf(csvRecord.get("Dostepnosc"));
                String color = csvRecord.get("Kolor");
                Integer mileage = Integer.valueOf(csvRecord.get("Przebieg"));
                Integer productionDate = Integer.valueOf(csvRecord.get("Rok produkcji"));
                cars.add(new CarModel(id, mark, model, price, bail, body, gearbox, numberOfSeats, numberOfDoors, fuelType, trunk, availability, color, mileage, productionDate));
            }
        }
        return cars;
    }
}
