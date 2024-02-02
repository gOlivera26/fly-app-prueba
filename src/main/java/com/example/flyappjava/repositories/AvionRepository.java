package com.example.flyappjava.repositories;

import com.example.flyappjava.entities.AvionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvionRepository extends JpaRepository<AvionEntity, Long> {
    List<AvionEntity> findByTipoAvionId(Long id);

    Optional<AvionEntity> findByMatricula(String matricula);
}
