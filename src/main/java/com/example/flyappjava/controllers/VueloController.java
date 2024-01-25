package com.example.flyappjava.controllers;

import com.example.flyappjava.dto.VueloRequest;
import com.example.flyappjava.dto.VueloResponse;
import com.example.flyappjava.models.DetalleVuelo;
import com.example.flyappjava.models.Vuelo;
import com.example.flyappjava.services.VueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vuelo")
public class VueloController {
    private final VueloService vueloService;

    @Autowired
    public VueloController(VueloService vueloService) {
        this.vueloService = vueloService;
    }
    @PostMapping("/createVuelo")
    public ResponseEntity<Vuelo> createVuelo(@RequestBody VueloRequest vuelo){
        Vuelo createdVuelo = vueloService.createVuelo(vuelo, vuelo.getDetalleVuelo());
        if(createdVuelo == null){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(createdVuelo);
        }
    }
    @GetMapping("/getVuelosConDetalle")
    public ResponseEntity<List<VueloResponse>> getVuelosDetalle(){
        List<VueloResponse> vuelos = vueloService.getVuelosConDetalle();
        if(vuelos == null){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(vuelos);
        }
    }
    @GetMapping("/getVueloConDetalleByNroVuelo/{numeroVuelo}")
    public ResponseEntity<VueloResponse> getVueloConDetalleByNroVuelo(@PathVariable String numeroVuelo){
        VueloResponse vuelo = vueloService.getVueloConDetalleByNroVuelo(numeroVuelo);
        if(vuelo == null){
            return ResponseEntity.badRequest().body(null);
        }
        else{
            return ResponseEntity.ok(vuelo);
        }
    }
}
