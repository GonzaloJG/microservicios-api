package com.msvc.usuario.controllers;

import com.msvc.usuario.entities.Usuario;
import com.msvc.usuario.service.UsuarioService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuarioRequest){
        Usuario usuario = usuarioService.saveUsuario(usuarioRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    int cantidadReintentos = 1;

    @GetMapping("/{usuarioId}")
    //@CircuitBreaker(name = "ratingHotelFallback", fallbackMethod = "ratingHotelFallback")
    @Retry(name = "ratingHotelFallback", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable String usuarioId){
        log.info("Listar un solo usuario: UsuarioController");
        log.info("Cantidad de reintentos: {}", cantidadReintentos);
        this.cantidadReintentos++;
        Usuario usuario = usuarioService.getUsuario(usuarioId);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    public ResponseEntity<Usuario> ratingHotelFallback (String usuarioId, Exception exception) {
    log.info("El respaldo se ejecuta porque el servicio está incativo: ", exception.getMessage());
    Usuario usuario = Usuario.builder()
            .email("root@gmail.com")
            .nombre("root")
            .informacion("Este usuario se crea por defecto cuando un servicio se cae")
            .usuarioId("1234")
            .build();
    return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
}
