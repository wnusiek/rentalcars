package com.example.rentalcars.service;

import com.example.rentalcars.model.ReturnModel;
import com.example.rentalcars.repository.ReturnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReturnService {

    private final ReturnRepository returnRepository;

    public void addReturn(ReturnModel returnModel) {
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
        return returnRepository.findById(id).orElse(null);
    }


}
