package com.msvc.usuario.service.impl;

import com.msvc.usuario.entities.Calificacion;
import com.msvc.usuario.entities.Hotel;
import com.msvc.usuario.entities.Usuario;
import com.msvc.usuario.exceptions.ResourceNotFoundException;
import com.msvc.usuario.external.services.HotelService;
import com.msvc.usuario.repository.UsuarioRepository;
import com.msvc.usuario.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UsuarioRepository usuarioRepo;

    //Para operar con OpenFeign
    private HotelService hotelService;

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        String randomUsuarioId = UUID.randomUUID().toString();
        usuario.setUsuarioId(randomUsuarioId);
        return usuarioRepo.save(usuario);
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepo.findAll();
    }

    @Override
    public Usuario getUsuario(String usuarioId) {
        Usuario usuario = usuarioRepo.findById(usuarioId).orElseThrow(
                () -> new ResourceNotFoundException("Usuario no encontrado con el ID: " + usuarioId)
        );
        Calificacion[] calificacionesDelUsuario = restTemplate.getForObject("http://CALIFICACION-SERVICE/calificaciones/usuarios/" + usuarioId, Calificacion[].class);

        log.info("{}", calificacionesDelUsuario);

        List<Calificacion> calificaciones = Arrays.stream(calificacionesDelUsuario).collect(Collectors.toList());

        List<Calificacion> listaCalificaciones = calificaciones.stream()
                        .map(calificacion -> {
                            System.out.print(calificacion.getHotelId());
                            //ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hoteles/"+ calificacion.getHotelId(), Hotel.class);
                            //Hotel hotel = forEntity.getBody();
                            //log.info("Respuesta con codigo de estado : {}", forEntity.getStatusCode());

                            Hotel hotel = hotelService.getHotel(calificacion.getHotelId());

                            calificacion.setHotel(hotel);

                            return calificacion;
                        }).collect(Collectors.toList());

        usuario.setCalificaciones(listaCalificaciones);
        return usuario;
    }
}
