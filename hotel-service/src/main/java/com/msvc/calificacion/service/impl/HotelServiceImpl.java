package com.msvc.calificacion.service.impl;

import com.msvc.calificacion.entity.Hotel;
import com.msvc.calificacion.exceptions.ResourceNotFoundException;
import com.msvc.calificacion.repository.HotelRepository;
import com.msvc.calificacion.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepo;
    @Override
    public Hotel create(Hotel hotel) {
        String hotelId = UUID.randomUUID().toString();
        hotel.setId(hotelId);
        return hotelRepo.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRepo.findAll();
    }

    @Override
    public Hotel get(String id) {
        return hotelRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Hotel no encontrado con el ID: " + id)
        );
    }
}
