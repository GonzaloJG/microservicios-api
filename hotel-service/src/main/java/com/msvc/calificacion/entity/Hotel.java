package com.msvc.calificacion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hotel")
public class Hotel {
    @Id
    @Column(name="id")
    private String id;
    @Column(name="nombre")
    private String nombre;
    @Column(name="informacion")
    private String informacion;
    @Column(name="ubicacion")
    private String ubicacion;
}
