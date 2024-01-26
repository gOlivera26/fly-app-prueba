package com.example.flyappjava.repositories;

import com.example.flyappjava.dto.VueloResponse;
import com.example.flyappjava.entities.VueloEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VueloRepository extends JpaRepository<VueloEntity, Long> {
    List<VueloEntity> findByFechaEquals(String fecha);

    boolean existsByNumeroVueloAndFecha(String numeroVuelo, String fecha);


    @Query("SELECT new com.example.flyappjava.dto.VueloResponse(v.id, v.origen, v.destino, v.numeroVuelo, v.fecha, av.tipoAvion.descripcion) " +
            "FROM VueloEntity v " +
            "JOIN v.avion av")
    List<VueloResponse> findAllWithDetails();


    @Query("SELECT new com.example.flyappjava.dto.VueloResponse(v.id, v.origen, v.destino, v.numeroVuelo, v.fecha, av.tipoAvion.descripcion) " +
            "FROM VueloEntity v " +
            "JOIN v.avion av " +
            "WHERE v.numeroVuelo = :numeroVuelo")
    List<VueloResponse> findByNumeroVueloWithDetails(@Param("numeroVuelo") String numeroVuelo);
    @Query("SELECT new com.example.flyappjava.dto.VueloResponse(v.id, v.origen, v.destino, v.numeroVuelo, v.fecha, av.tipoAvion.descripcion) " +
            "FROM VueloEntity v " +
            "JOIN v.avion av " +
            "WHERE v.fecha = :fecha")
    List<VueloResponse> findByFechaVueloWithDetails(@Param("fecha") String fecha);


}

