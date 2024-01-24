package com.example.flyappjava.services;

import com.example.flyappjava.models.Avion;
import com.example.flyappjava.models.TipoAvion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AvionService {
    Avion createAvion(Avion avion);
    TipoAvion createTipoAvion(TipoAvion tipoAvion);
    TipoAvion updateTipoAvion(TipoAvion tipoAvion);
    Avion updateAvion(Avion avion);
    TipoAvion deleteTipoAvion(Long id);
    Avion deleteAvionById(Long id);
    List<Avion> getAllAviones();
    List<TipoAvion> getAllTipoAviones();
    List<Avion> getAvionByTipoAvion(Long id);
}
