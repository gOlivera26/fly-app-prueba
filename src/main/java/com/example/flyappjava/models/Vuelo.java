package com.example.flyappjava.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vuelo {
    private Long id;
    private String numeroVuelo;
    private String origen;
    private String destino;
    private String fecha;
    private Avion avion;
    private List<DetalleVuelo> detalleVuelo;
}
