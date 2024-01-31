package com.example.flyappjava.controllers;

import com.example.flyappjava.models.Pasajero;
import com.example.flyappjava.models.TipoDocumento;
import com.example.flyappjava.services.PasajeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pasajero")
public class PasajeroController {
    private final PasajeroService pasajeroService;

    @Autowired
    public PasajeroController(PasajeroService pasajeroService) {
        this.pasajeroService = pasajeroService;
    }
    @PostMapping("/createPasajero")
    public ResponseEntity<Pasajero> createPasajero(@RequestBody Pasajero pasajero){
        Pasajero createdPasajero = pasajeroService.createPasajero(pasajero);
        if(createdPasajero == null){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(createdPasajero);
        }
    }
    @GetMapping("/getAllPasajeros")
    public ResponseEntity<List<Pasajero>> getAllPasajero(){
        List<Pasajero> pasajeros = pasajeroService.getAllPasajero();
        if(pasajeros == null){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(pasajeros);
        }
    }
    @PostMapping("/createTipoDocumento")
    public ResponseEntity<TipoDocumento> createTipoDocumento(@RequestBody TipoDocumento tipoDocumento){
        TipoDocumento createdTipoDocumento = pasajeroService.createTipoDocumento(tipoDocumento);
        if(createdTipoDocumento == null){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(createdTipoDocumento);
        }
    }
    @PutMapping("/updateTipoDocumento")
    public ResponseEntity<TipoDocumento> updateTipoDocumento(@RequestBody TipoDocumento tipoDocumento){
        TipoDocumento updatedTipoDocumento = pasajeroService.updateTipoDocumento(tipoDocumento);
        if(updatedTipoDocumento == null){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(updatedTipoDocumento);
        }
    }
    @GetMapping("/getAllTipoDocumento")
    public ResponseEntity<List<TipoDocumento>> getAllTipoDocumento(){
        List<TipoDocumento> tipoDocumentos = pasajeroService.getAllTipoDocumento();
        if(tipoDocumentos == null){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(tipoDocumentos);
        }
    }
    @GetMapping("/getPasajeroByNroDoc/{numeroDocumento}")
    public ResponseEntity<Pasajero> getPasajeroByNroDoc(@PathVariable String numeroDocumento){
        Pasajero pasajero = pasajeroService.getPasajeroByNroDoc(numeroDocumento);
        if(pasajero == null){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(pasajero);
        }
    }
    @PutMapping("/cambiarEstado/{numeroDocumento}")
    public Boolean cambiarEstado(@PathVariable String numeroDocumento){
        if(numeroDocumento.isEmpty()){
            throw new RuntimeException("El numero de documento no puede ser nulo");
        }
        else{
            return pasajeroService.cambiarEstado(numeroDocumento);
        }
    }

}
