package com.example.rentalcars.service;

import com.example.rentalcars.Exceptions.ReturnAdditionException;
import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.model.ReturnModel;
import com.example.rentalcars.repository.ReturnRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReturnServiceTests {
    @Mock
    private ReturnRepository returnRepository;
    @InjectMocks
    private ReturnService returnService;
    private ReturnModel returnModel0;
    private ReservationModel reservationModel;
    private Long returnId;

    @BeforeEach
    public void setup() {
        returnModel0 = new ReturnModel(
                1L,
                new EmployeeModel(),
                LocalDate.of(2024, 5, 1),
                new ReservationModel(),
                BigDecimal.valueOf(0),
                BigDecimal.valueOf(100),
                "Jakiś komentarz"
        );
        reservationModel = new ReservationModel();
        returnModel0.setReservation(reservationModel);
        returnId = 1L;
    }

    @Test
    public void testAddReturn_Success(){
        given(returnRepository.save(returnModel0)).willReturn(returnModel0);
        var savedReturn = returnService.addReturn(returnModel0);
        assertThat(savedReturn).isNotNull();
    }

    @Test
    public void testAddReturn_ExceptionThrown(){
        given(returnRepository.save(returnModel0)).willThrow(new RuntimeException());
        assertThrows(ReturnAdditionException.class, () -> returnService.addReturn(returnModel0));
    }

    @Test
    public void testGetReturnModelList_ListReturned() {
        given(returnRepository.findAll()).willReturn(List.of(returnModel0, new ReturnModel(), new ReturnModel()));
        var returnModelList = returnService.getReturnModelList();
        assertThat(returnModelList.size()).isEqualTo(3);
    }

    @Test
    public void testGetReturnModelList_EmptyListReturned() {
        given(returnRepository.findAll()).willReturn(List.of());
        var returnModelList = returnService.getReturnModelList();
        assertThat(returnModelList).isEmpty();
    }

    @Test
    public void testUpdateReturn() {
        given(returnRepository.save(returnModel0)).willReturn(returnModel0);
        returnService.updateReturn(returnModel0);
        verify(returnRepository, times(1)).save(returnModel0);
    }

    @Test
    public void testDeleteReturn() {
        willDoNothing().given(returnRepository).deleteById(returnId);
        returnService.deleteReturn(returnId);
        verify(returnRepository, times(1)).deleteById(returnId);
    }

    @Test
    public void testFindById_Found() {
        given(returnRepository.findById(returnId)).willReturn(Optional.of(returnModel0));
        var savedReturn = returnService.findById(returnId);
        assertThat(savedReturn).isEqualTo(returnModel0);
    }

    @Test
    public void testFindById_ExceptionThrown() {
        given(returnRepository.findById(returnId)).willThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> returnService.findById(returnId));
    }

    @Test
    public void testFindById_FindByReservation_Found() {
        given(returnRepository.findByReservation(reservationModel)).willReturn(Optional.of(returnModel0));
        var savedReturn = returnService.findByReservation(reservationModel);
        assertThat(savedReturn).isEqualTo(returnModel0);
    }

    @Test
    public void testGetIncome() {
        var returnModel1 = new ReturnModel();
        returnModel1.setTotalCost(BigDecimal.valueOf(400.2));
        var returnModel2 = new ReturnModel();
        returnModel2.setTotalCost(BigDecimal.valueOf(500.3));
        var savedIncome = returnService.getIncome(List.of(returnModel0, returnModel1, returnModel2));
        assertThat(savedIncome).isEqualTo(BigDecimal.valueOf(1000.5));
    }

    @Test
    public void testGetIncome_EmptyList() {
        var savedIncome = returnService.getIncome(List.of());
        assertThat(savedIncome).isEqualTo(BigDecimal.valueOf(0));
    }

    @Test
    public void testFindById_FindByReservation_ExceptionThrown() {
        given(returnRepository.findByReservation(reservationModel)).willThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> returnService.findByReservation(reservationModel));
    }

    @Test
    public void testGetReturnListWithFilters_NoCriteria_NoEmptyResult() {
        given(returnRepository.findWithFilters(null, null, null, null)).willReturn(List.of(new ReturnModel(), new ReturnModel()));
        var savedReturn = returnService.getReturnListWithFilters(null, null, null, null);
        assertThat(savedReturn.size()).isEqualTo(2);
    }
}
