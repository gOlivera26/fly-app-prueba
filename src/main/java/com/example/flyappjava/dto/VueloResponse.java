package com.example.flyappjava.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VueloResponse {
        private Long vueloId;
        private String origen;
        private String destino;
        private String numeroVuelo;
        private String fecha;
        private List<DetalleVueloResponse> detalleVueloResponses;
        private String descripcionAvion;

        public VueloResponse(Long vueloId, String origen, String destino, String numeroVuelo, String fecha, String descripcionAvion) {
                this.vueloId = vueloId;
                this.origen = origen;
                this.destino = destino;
                this.numeroVuelo = numeroVuelo;
                this.fecha = fecha;
                this.descripcionAvion = descripcionAvion;
        }
}
