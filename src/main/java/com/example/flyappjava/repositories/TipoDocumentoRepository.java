package com.example.flyappjava.repositories;

import com.example.flyappjava.entities.TipoDocumentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumentoEntity, Long> {
}
