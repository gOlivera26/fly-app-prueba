package com.example.flyappjava.services.Impl;

import com.example.flyappjava.dto.DetalleVueloRequest;
import com.example.flyappjava.dto.VueloRequest;
import com.example.flyappjava.entities.AvionEntity;
import com.example.flyappjava.entities.DetalleVueloEntity;
import com.example.flyappjava.entities.PasajeroEntity;
import com.example.flyappjava.entities.VueloEntity;
import com.example.flyappjava.models.DetalleVuelo;
import com.example.flyappjava.models.Vuelo;
import com.example.flyappjava.repositories.AvionRepository;
import com.example.flyappjava.repositories.DetalleVueloRepository;
import com.example.flyappjava.repositories.PasajeroRepository;
import com.example.flyappjava.repositories.VueloRepository;
import com.example.flyappjava.services.VueloService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VueloServiceImpl implements VueloService {

    private final PasajeroRepository pasajeroRepository;
    private final AvionRepository avionRepository;
    private final DetalleVueloRepository detalleVueloRepository;
    private final ModelMapper modelMapper;
    private final VueloRepository vueloRepository;

    @Autowired
    public VueloServiceImpl(PasajeroRepository pasajeroRepository, AvionRepository avionRepository, DetalleVueloRepository detalleVueloRepository, ModelMapper modelMapper,
                            VueloRepository vueloRepository) {
        this.pasajeroRepository = pasajeroRepository;
        this.avionRepository = avionRepository;
        this.detalleVueloRepository = detalleVueloRepository;
        this.modelMapper = modelMapper;
        this.vueloRepository = vueloRepository;
    }

   /* @Override
    public Vuelo createVuelo(VueloRequest vuelo, List<DetalleVueloRequest> detallesVuelo) {
        VueloEntity vueloEntity = modelMapper.map(vuelo, VueloEntity.class);
        AvionEntity avionEntity = avionRepository.findById(vuelo.getAvion()).orElse(null);

        if (avionEntity == null) {
            throw new RuntimeException("El avion no existe");
        }

        vueloEntity.setAvion(avionEntity);
        vueloRepository.save(vueloEntity);

        for (DetalleVueloRequest detalleVueloLista : detallesVuelo) {
            DetalleVueloEntity detalleVueloEntity = modelMapper.map(detalleVueloLista, DetalleVueloEntity.class);

            PasajeroEntity pasajeroEntity = pasajeroRepository.findById(detalleVueloLista.getPasajero()).orElse(null);

            if (pasajeroEntity == null) {
                throw new RuntimeException("El pasajero no existe");
            } else {
                detalleVueloEntity.setPasajero(pasajeroEntity);
            }

            detalleVueloEntity.setVuelo(vueloEntity);

            detalleVueloRepository.save(detalleVueloEntity);
            vueloEntity.getDetallesVuelos().add(detalleVueloEntity);
        }

        vueloRepository.save(vueloEntity);

        Vuelo vueloResult = modelMapper.map(vueloRepository.save(vueloEntity), Vuelo.class);

        return vueloResult;
    }

    */
   @Override
   @Transactional
   public Vuelo createVuelo(VueloRequest vuelo, List<DetalleVueloRequest> detallesVuelo) {
       VueloEntity vueloEntity = modelMapper.map(vuelo, VueloEntity.class);
       AvionEntity avionEntity = avionRepository.findById(vuelo.getAvion()).orElse(null);

       if (avionEntity == null) {
           throw new RuntimeException("El avion no existe");
       }

       vueloEntity.setAvion(avionEntity);
       vueloRepository.save(vueloEntity);

       for (DetalleVueloRequest detalleVueloLista : detallesVuelo) {
           DetalleVueloEntity detalleVueloEntity = modelMapper.map(detalleVueloLista, DetalleVueloEntity.class);

           PasajeroEntity pasajeroEntity = pasajeroRepository.findById(detalleVueloLista.getPasajero()).orElse(null);

           if (pasajeroEntity != null){
               // Verificar si el pasajero ya ha sido asignado al vuelo
               boolean pasajeroYaAsignado = vueloEntity.getDetallesVuelos().stream()
                       .anyMatch(detalle -> detalle.getPasajero().getId().equals(pasajeroEntity.getId()));

               if(pasajeroYaAsignado){
                   throw new RuntimeException("No se puede asignar el mismo pasajero en el mismo vuelo");
               }

               detalleVueloEntity.setPasajero(pasajeroEntity);
               detalleVueloEntity.setVuelo(vueloEntity);

               detalleVueloRepository.save(detalleVueloEntity);
               vueloEntity.getDetallesVuelos().add(detalleVueloEntity);
           }else{
               throw new RuntimeException("El pasajero no existe");
           }
       }
       vueloRepository.save(vueloEntity);

       Vuelo vueloResult = modelMapper.map(vueloEntity, Vuelo.class);

       // Mapeo manual de la lista de DetalleVuelo
       List<DetalleVuelo> detalleVueloList = vueloEntity.getDetallesVuelos().stream()
               .map(detalleVueloEntity -> modelMapper.map(detalleVueloEntity, DetalleVuelo.class))
               .collect(Collectors.toList());

       return vueloResult.builder()
               .id(vueloResult.getId())
               .avion(vueloResult.getAvion())
               .origen(vueloResult.getOrigen())
               .destino(vueloResult.getDestino())
               .numeroVuelo(vueloResult.getNumeroVuelo())
               .fecha(vueloResult.getFecha())
               .detalleVuelo(detalleVueloList) // Setea la lista de DetalleVuelo
               .build();
   }
}
