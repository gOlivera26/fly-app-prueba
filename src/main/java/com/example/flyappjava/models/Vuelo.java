package com.example.flyappjava.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vuelo {
    private Long id;
    private String numeroVuelo;
    private String origen;
    private String destino;
    private LocalDate fecha;
    private LocalTime hora;
    private Avion avion;
}
