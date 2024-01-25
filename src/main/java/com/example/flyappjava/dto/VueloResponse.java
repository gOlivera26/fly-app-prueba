package com.example.flyappjava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VueloResponse {
        private Long vueloId;
        private String origen;
        private String destino;
        private String numeroVuelo;
        private String fecha;
        private String nombrePasajero;
        private String apellidoPasajero;
        private String numeroAsiento;
        private String descripcionAvion;
}
