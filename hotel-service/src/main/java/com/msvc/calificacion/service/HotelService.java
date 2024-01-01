package com.msvc.calificacion.service;

import com.msvc.calificacion.entity.Hotel;

import java.util.List;

public interface HotelService {
    Hotel create (Hotel hotel);
    List<Hotel> getAll();
    Hotel get(String id);
}
