package com.example.flyappjava.services;

import com.example.flyappjava.dto.DetalleVueloRequest;
import com.example.flyappjava.dto.VueloRequest;
import com.example.flyappjava.dto.VueloResponse;
import com.example.flyappjava.models.DetalleVuelo;
import com.example.flyappjava.models.Vuelo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VueloService {
    Vuelo createVuelo(VueloRequest vuelo, List<DetalleVueloRequest> detalleVuelo);
    List<VueloResponse> getVuelosConDetalle();
    VueloResponse getVueloConDetalleByNroVuelo(String numeroVuelo);
}
