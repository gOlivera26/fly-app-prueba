package com.example.flyappjava.repositories;

import com.example.flyappjava.entities.TipoAvionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoAvionRepository extends JpaRepository<TipoAvionEntity, Long> {

    Optional<TipoAvionEntity> findByDescripcion(String descripcion);
}
