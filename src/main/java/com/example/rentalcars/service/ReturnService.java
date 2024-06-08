package com.example.rentalcars.service;

import com.example.rentalcars.Exceptions.ReturnAdditionException;
import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.enums.ReservationStatus;
import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.model.ReturnModel;
import com.example.rentalcars.repository.ReturnRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReturnService {

    private final ReturnRepository returnRepository;
    private final DepartmentService departmentService;
    private final CarService carService;

    public ReturnModel addReturn(ReturnModel returnModel) {
        try {
            ReturnModel savedReturn = returnRepository.save(returnModel);
            System.out.println("Zwrot został dodany pomyślnie");
            return savedReturn;
        } catch (Exception e) {
            throw new ReturnAdditionException("Błąd podczas dodawania wypożyczenia", e);
        }

    }

    public void addReturn(ReturnModel returnModel, BigDecimal supplement) {
        returnModel.setSupplement(supplement);
        returnModel.setTotalCost(returnModel.getReservation().getPrice().add(returnModel.getSupplement()));
        departmentService.addCarToDepartment(returnModel.getReservation().getCar().getId(), returnModel.getReservation().getReturnVenue().getId());
        carService.setCarStatus(returnModel.getReservation().getCar().getId(), CarStatus.AVAILABLE);
        returnRepository.save(returnModel);
    }

    public List<ReturnModel> getReturnModelList() {
        return returnRepository.findAll();
    }

    public void updateReturn(ReturnModel returnModel) {
        returnRepository.save(returnModel);
    }

    public void deleteReturn(Long id) {
        returnRepository.deleteById(id);
    }

    public ReturnModel findById(Long id) {
        var returnModel = returnRepository.findById(id);
        return returnModel.orElseThrow(() -> new EntityNotFoundException("Nie znaleziono zwrotu" + id));
    }

    public BigDecimal getIncome(List<ReturnModel> returnModelList) {
        return returnModelList.stream()
                .map(ReturnModel::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<YearMonth, BigDecimal> getMonthlyIncome(List<ReturnModel> returnModelList){
        return getReturnModelList().stream()
                .collect(Collectors.groupingBy(
                        returnModel -> YearMonth.from(returnModel.getDateOfReturn()), Collectors.reducing(BigDecimal.ZERO, ReturnModel::getTotalCost, BigDecimal::add)
                ));
    }

    public ReturnModel findByReservation(ReservationModel reservationModel) {
        var ret = returnRepository.findByReservation(reservationModel);
        return ret.orElseThrow(() -> new EntityNotFoundException("Nie znaleziono zwrotu."));
    }

    public List<ReturnModel> getReturnListWithFilters(CustomerModel customer, LocalDate date, DepartmentModel receptionVenue, ReservationStatus reservationStatus) {
        return returnRepository.findWithFilters(customer, date, receptionVenue, reservationStatus);
    }
}
