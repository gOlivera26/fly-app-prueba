package com.example.flyappjava.repositories;

import com.example.flyappjava.entities.DetalleVueloEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleVueloRepository extends JpaRepository<DetalleVueloEntity, Long> {
    List<DetalleVueloEntity> findByVueloId(Long vueloId);
}
