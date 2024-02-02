package com.example.flyappjava.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "aviones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String matricula;
    @ManyToOne
    @JoinColumn(name = "tipo_avion_id")
    private TipoAvionEntity tipoAvion;
    @Column
    private Integer capacidad;
    @Column
    private Boolean estado;
}
