package com.example.flyappjava.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVuelo {
    private Long id;
    private Integer numeroAsiento;
    private Pasajero pasajero;
}
