package com.example.flyappjava.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vuelos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VueloEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "avion_id")
    private AvionEntity avion;
    @Column
    private String origen;
    @Column
    private String destino;
    @Column
    private String numeroVuelo;
    @Column
    private String fecha;

    @OneToMany(mappedBy = "vuelo", cascade = CascadeType.ALL)
    private List<DetalleVueloEntity> detallesVuelos = new ArrayList<>();

}
