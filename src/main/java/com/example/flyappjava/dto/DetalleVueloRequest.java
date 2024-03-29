package com.example.flyappjava.dto;

import com.example.flyappjava.models.Pasajero;
import com.example.flyappjava.models.Vuelo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVueloRequest {
    private Long id;
    private Long vuelo;
    private Integer numeroAsiento;
    private Long pasajero;
}
