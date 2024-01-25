package com.example.flyappjava.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetalleVueloResponse {
    private String nombrePasajero;
    private String apellidoPasajero;
    private String numeroAsiento;
}
