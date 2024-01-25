package com.example.flyappjava.repositories;

import com.example.flyappjava.entities.VueloEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VueloRepository extends JpaRepository<VueloEntity, Long> {
    @Query("select v from VueloEntity v where lower(v.fecha) = lower(?1) and lower(v.numeroVuelo) = lower(?2)")
    List<VueloEntity> findByFechaEqualsAndNumeroVueloEquals(String fecha, String numeroVuelo);

    List<VueloEntity> findByFechaEquals(String fecha);

    boolean existsByNumeroVueloAndFecha(String numeroVuelo, String fecha);
}
