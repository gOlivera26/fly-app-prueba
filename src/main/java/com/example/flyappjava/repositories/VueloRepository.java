package com.example.flyappjava.repositories;

import com.example.flyappjava.dto.VueloResponse;
import com.example.flyappjava.entities.VueloEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VueloRepository extends JpaRepository<VueloEntity, Long> {
    List<VueloEntity> findByFechaEquals(String fecha);

    boolean existsByNumeroVueloAndFecha(String numeroVuelo, String fecha);


        @Query("SELECT new com.example.flyappjava.dto.VueloResponse(v.id, v.origen, v.destino, v.numeroVuelo, v.fecha, p.nombre, p.apellido, dv.numeroAsiento, tp.descripcion) " +
                "FROM VueloEntity v " +
                "JOIN v.detallesVuelos dv " +
                "JOIN dv.pasajero p " +
                "JOIN v.avion av " +
                "JOIN av.tipoAvion tp")
        List<VueloResponse> findAllWithDetails();

}
