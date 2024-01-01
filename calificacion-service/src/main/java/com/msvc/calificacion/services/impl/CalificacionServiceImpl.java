package com.msvc.calificacion.services.impl;

import com.msvc.calificacion.entity.Calificacion;
import com.msvc.calificacion.repository.CalificacionRepository;
import com.msvc.calificacion.services.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
public class CalificacionServiceImpl implements CalificacionService {
    @Autowired
    private CalificacionRepository calificacionRepo;

    @Override
    public Calificacion create(Calificacion calificacion) {
        return calificacionRepo.save(calificacion);
    }

    @Override
    public List<Calificacion> getCalificaciones() {
        return calificacionRepo.findAll();
    }

    @Override
    public List<Calificacion> getCalificacionesByUsuarioId(String usuarioId) {
        return calificacionRepo.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Calificacion> getCalificacionesByHotelId(String hotelId) {
        return calificacionRepo.findByHotelId(hotelId);
    }

    @Override
    public Calificacion update(String calificacionId, Calificacion calificacion) {
        Calificacion calif = calificacionRepo.findById(calificacionId).orElseThrow(
                () -> new ResolutionException("No encontró la calificacion con el ID:" + calificacionId)
        );

        calif.setUsuarioId(calificacion.getUsuarioId());
        calif.setHotelId(calificacion.getHotelId());
        calif.setCalificacion(calificacion.getCalificacion());
        calif.setObservaciones(calificacion.getObservaciones());

        return calificacionRepo.save(calif);
    }

    @Override
    public void delete(String calificacionId) {
        calificacionRepo.deleteById(calificacionId);
    }


}
