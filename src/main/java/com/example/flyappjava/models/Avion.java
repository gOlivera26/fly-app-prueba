package com.example.flyappjava.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Avion {
    private Long id;
    private String matricula;
    private TipoAvion tipoAvion;
    private Integer capacidad;
}
