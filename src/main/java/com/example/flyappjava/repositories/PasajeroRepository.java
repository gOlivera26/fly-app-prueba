package com.example.flyappjava.repositories;

import com.example.flyappjava.entities.PasajeroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasajeroRepository extends JpaRepository<PasajeroEntity, Long> {
}
