package com.example.rentalcars.service;

import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public void addReservation(ReservationModel reservation){
        reservationRepository.save(reservation)
    }

    public List<ReservationModel> getReservationList(){
        return reservationRepository.findAll();
    }

    public void editReservation(ReservationModel editReservation){
        reservationRepository.save(editReservation);
    }

    public void removeReservation(Long id){
        reservationRepository.deleteById(id);
    }

    public ReservationModel findbyId(Long id){
        return reservationRepository.findById(id).orElse(null);
    }
}
