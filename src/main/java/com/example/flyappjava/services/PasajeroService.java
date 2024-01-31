package com.example.flyappjava.services;

import com.example.flyappjava.models.Pasajero;
import com.example.flyappjava.models.TipoDocumento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PasajeroService {
    Pasajero createPasajero(Pasajero pasajero);
    List<Pasajero> getAllPasajero();
    Pasajero getPasajeroByNroDoc(String numeroDocumento);
    Boolean cambiarEstado(String numeroDocumento);
    TipoDocumento createTipoDocumento(TipoDocumento tipoDocumento);
    TipoDocumento updateTipoDocumento(TipoDocumento tipoDocumento);
    List<TipoDocumento> getAllTipoDocumento();
}
