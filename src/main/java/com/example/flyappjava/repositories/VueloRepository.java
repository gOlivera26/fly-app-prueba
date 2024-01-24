package com.example.flyappjava.repositories;

import com.example.flyappjava.entities.VueloEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VueloRepository extends JpaRepository<VueloEntity, Long> {
}
