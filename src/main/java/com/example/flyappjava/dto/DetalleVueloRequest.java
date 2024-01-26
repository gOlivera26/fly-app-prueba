package com.example.flyappjava.dto;

import com.example.flyappjava.models.Pasajero;
import com.example.flyappjava.models.Vuelo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVueloRequest {
    private Long id;
    private String numeroAsiento;
    private Long pasajero;
}
