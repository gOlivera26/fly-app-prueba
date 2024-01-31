package com.example.flyappjava.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pasajeros")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasajeroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @ManyToOne
    @JoinColumn(name = "tipo_documento_id")
    private TipoDocumentoEntity tipoDocumento;
    @Column
    private String numeroDocumento;
    @Column
    private String email;
    @Column
    private Boolean estado; // true = activo, false = inactivo
}
