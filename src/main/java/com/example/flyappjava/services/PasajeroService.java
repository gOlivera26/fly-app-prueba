package com.example.flyappjava.services;

import com.example.flyappjava.models.Pasajero;
import com.example.flyappjava.models.TipoDocumento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PasajeroService {
    Pasajero createPasajero(Pasajero pasajero);
    List<Pasajero> getAllPasajero();
    TipoDocumento createTipoDocumento(TipoDocumento tipoDocumento);
    TipoDocumento updateTipoDocumento(TipoDocumento tipoDocumento);
}
