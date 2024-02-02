package com.example.flyappjava.repositories;

import com.example.flyappjava.entities.PasajeroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasajeroRepository extends JpaRepository<PasajeroEntity, Long> {
    PasajeroEntity findByNumeroDocumento(String numeroDocumento);

    @Query("select p from PasajeroEntity p where p.estado = ?1")
    List<PasajeroEntity> findByEstado(Boolean estado);

    PasajeroEntity findByEmail(String email);
}
