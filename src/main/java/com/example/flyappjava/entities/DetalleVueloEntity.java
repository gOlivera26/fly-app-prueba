package com.example.flyappjava.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalles_vuelo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVueloEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vuelo_id")
    private VueloEntity vuelo;

    @ManyToOne
    @JoinColumn(name = "pasajero_id")
    private PasajeroEntity pasajero;

    @Column
    private String numeroAsiento;
}
