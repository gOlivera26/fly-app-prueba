package com.example.flyappjava.controllers;

import com.example.flyappjava.models.Avion;
import com.example.flyappjava.models.TipoAvion;
import com.example.flyappjava.services.AvionService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avion")
public class AvionController {
    private final AvionService avionService;
    @Autowired
    public AvionController(AvionService avionService) {
        this.avionService = avionService;
    }
    @PostMapping("/createAvion")
    public ResponseEntity<Avion> createAvion(@RequestBody  Avion avion) {
        Avion createdAvion = avionService.createAvion(avion);
        if(createdAvion == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(createdAvion);
    }
    @PostMapping("/createTipoAvion")
    public ResponseEntity<TipoAvion> createTipoAvion (@RequestBody TipoAvion tipoAvion){
        TipoAvion tipoAvionSaved = avionService.createTipoAvion(tipoAvion);
        if(tipoAvionSaved == null){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(tipoAvionSaved);
        }
    }
    @GetMapping("/getAllAviones")
    public ResponseEntity<List<Avion>> getAllAviones(){
        List<Avion> aviones = avionService.getAllAviones();
        if(aviones == null){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(aviones);
        }
    }
    @GetMapping("/getAllTipoAviones")
    public ResponseEntity<List<TipoAvion>> getAllTipoAviones(){
        List<TipoAvion> tipoAviones = avionService.getAllTipoAviones();
        if(tipoAviones == null){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(tipoAviones);
        }
    }
    @GetMapping("/getAvionByMatricula/{matricula}")
    public ResponseEntity<Avion> getAvionByMatricula(@PathVariable("matricula") String matricula){
        Avion avion = avionService.getAvionByMatricula(matricula);
        if(avion == null){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(avion);
        }
    }
    @PutMapping("/updateTipoAvion")
    public ResponseEntity<TipoAvion> updateTipoAvion(@RequestBody TipoAvion tipoAvion){
        TipoAvion tipoAvionSaved = avionService.updateTipoAvion(tipoAvion);
        if(tipoAvionSaved == null){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(tipoAvionSaved);
        }
    }
    @PutMapping("/updateAvion")
    public ResponseEntity<Avion> updateAvion(@RequestBody Avion avion){
        Avion avionSaved = avionService.updateAvion(avion);
        if(avionSaved == null){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(avionSaved);
        }
    }
    @DeleteMapping("/deleteTipoAvion/{id}")
    public ResponseEntity<TipoAvion> deleteTipoAvionById(@Param("id") Long id){
        TipoAvion tipoAvionSaved = avionService.deleteTipoAvion(id);
        if(tipoAvionSaved == null){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(tipoAvionSaved);
        }
    }
    @DeleteMapping("/deleteAvion/{id}")
    public ResponseEntity<Avion> deleteAvionById(Long id){
        Avion avionSaved = avionService.deleteAvionById(id);
        if(avionSaved == null){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(avionSaved);
        }
    }

    @GetMapping("/getAvionByTipoAvion/{id}")
    public ResponseEntity<List<Avion>> getAvionByTipoAvion(@PathVariable("id") Long id){
        List<Avion> aviones = avionService.getAvionByTipoAvion(id);
        if(aviones == null){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(aviones);
        }
    }
}
