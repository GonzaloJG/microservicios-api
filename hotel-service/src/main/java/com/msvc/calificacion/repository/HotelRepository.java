package com.msvc.calificacion.repository;

import com.msvc.calificacion.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, String> {
}
