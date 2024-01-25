package com.example.flyappjava.dto;

import com.example.flyappjava.models.Avion;
import com.example.flyappjava.models.DetalleVuelo;
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
public class VueloRequest {
    private Long id;
    private String numeroVuelo;
    private String origen;
    private String destino;
    private String fecha;
    private Long avion;
    private List<DetalleVueloRequest> detalleVuelo;
}
