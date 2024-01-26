package com.example.flyappjava.services.Impl;

import com.example.flyappjava.dto.DetalleVueloRequest;
import com.example.flyappjava.dto.DetalleVueloResponse;
import com.example.flyappjava.dto.VueloRequest;
import com.example.flyappjava.dto.VueloResponse;
import com.example.flyappjava.entities.AvionEntity;
import com.example.flyappjava.entities.DetalleVueloEntity;
import com.example.flyappjava.entities.PasajeroEntity;
import com.example.flyappjava.entities.VueloEntity;
import com.example.flyappjava.models.DetalleVuelo;
import com.example.flyappjava.models.Pasajero;
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

    @Override
    @Transactional
    public Vuelo createVuelo(VueloRequest vuelo, List<DetalleVueloRequest> detallesVuelo) {
        // Verificar si el número de vuelo ya existe para la misma fecha
        boolean numeroVueloExisteMismaFecha = vueloRepository.existsByNumeroVueloAndFecha(vuelo.getNumeroVuelo(), vuelo.getFecha());
        if(numeroVueloExisteMismaFecha){
            throw new RuntimeException("El numero de vuelo ya existe para la misma fecha");
        }

        VueloEntity vueloEntity = modelMapper.map(vuelo, VueloEntity.class);
        AvionEntity avionEntity = avionRepository.findById(vuelo.getAvion()).orElse(null);

        if (avionEntity == null) {
            throw new RuntimeException("El avion no existe");
        }

        // Verificar si el avión ya ha sido asignado para la misma fecha
        boolean avionYaAsignadoMismaFecha = vueloRepository.findByFechaEquals(vuelo.getFecha()).stream()
                .anyMatch(v -> v.getAvion().getId().equals(avionEntity.getId()));
        if(avionYaAsignadoMismaFecha){
            throw new RuntimeException("El avion ya ha sido asignado para la misma fecha");
        }

        vueloEntity.setAvion(avionEntity);
        vueloRepository.save(vueloEntity);

        int contadorPasajeros = 0;
        for (DetalleVueloRequest detalleVueloLista : detallesVuelo) {
            int contadorListaTotalPasajeros = detallesVuelo.size();
            if(contadorPasajeros >= avionEntity.getCapacidad()){
                throw new RuntimeException("El avion "+ avionEntity.getTipoAvion().getDescripcion()+" no tiene capacidad para más pasajeros.|CANTIDAD LIMITE AVION: "+avionEntity.getCapacidad()+"|CANTIDAD AGREGADA PASAJEROS: "+contadorListaTotalPasajeros);
            }

            DetalleVueloEntity detalleVueloEntity = modelMapper.map(detalleVueloLista, DetalleVueloEntity.class);

            PasajeroEntity pasajeroEntity = pasajeroRepository.findById(detalleVueloLista.getPasajero()).orElse(null);

            if (pasajeroEntity != null){

                // Verificar si el pasajero ya ha sido asignado al vuelo
                boolean pasajeroYaAsignado = vueloEntity.getDetallesVuelos().stream()
                        .anyMatch(detalle -> detalle.getPasajero().getId().equals(pasajeroEntity.getId()));
                if(pasajeroYaAsignado){
                    throw new RuntimeException("No se puede asignar el mismo pasajero en el mismo vuelo");
                }

                //Verificar si el asiento no fue designado a otra persona
                boolean asientoAsignado = vueloEntity.getDetallesVuelos().stream()
                                .anyMatch(detalle -> detalle.getNumeroAsiento().equals(detalleVueloLista.getNumeroAsiento()));

                //Buscar el pasajero que tiene asignado el asiento
                PasajeroEntity pasajeroAsignado = vueloEntity.getDetallesVuelos().stream()
                        .filter(detalle -> detalle.getNumeroAsiento().equals(detalleVueloLista.getNumeroAsiento()))
                        .map(DetalleVueloEntity::getPasajero)
                        .findFirst()
                        .orElse(null);

                if(asientoAsignado){
                    throw new RuntimeException("El asiento: "+detalleVueloLista.getNumeroAsiento()+" ,ya ha sido asignado a: "+pasajeroAsignado.getNombre()+" "+pasajeroAsignado.getApellido()+" Tipo Documento: "+pasajeroAsignado.getTipoDocumento().getDescripcion()+" Número Documento: "+pasajeroAsignado.getNumeroDocumento());
                }

                detalleVueloEntity.setPasajero(pasajeroEntity);
                detalleVueloEntity.setVuelo(vueloEntity);

                detalleVueloRepository.save(detalleVueloEntity);
                vueloEntity.getDetallesVuelos().add(detalleVueloEntity);
                contadorPasajeros++;
            }else{
                throw new RuntimeException("El pasajero no existe");
            }
        }

        if(compararOrigenDestino(vueloEntity.getOrigen(), vueloEntity.getDestino())){
            throw new RuntimeException("El origen y el destino no pueden ser iguales");
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

    @Override
    public List<VueloResponse> getVuelosConDetalle() {
        List<VueloResponse> vuelos = vueloRepository.findAllWithDetails();
        if(vuelos.isEmpty()){
            throw new RuntimeException("No se encontraron vuelos");
        }

        for (VueloResponse vuelo : vuelos) {
            List<DetalleVueloEntity> detallesVuelo = detalleVueloRepository.findByVueloId(vuelo.getVueloId());

            // Mapeamos cada detalle del vuelo a un DetalleVueloResponse y los guardamos en una lista
            List<DetalleVueloResponse> detalleVueloResponses = detallesVuelo.stream()
                    .map(detalle -> {
                        PasajeroEntity pasajero = detalle.getPasajero();
                        if (pasajero != null) {
                            return new DetalleVueloResponse(pasajero.getNombre(), pasajero.getApellido(), detalle.getNumeroAsiento());
                        } else {
                            return null;
                        }
                    })
                    .filter(obj -> obj != null)
                    .collect(Collectors.toList());

            vuelo.setDetalleVueloResponses(detalleVueloResponses);
        }
        return vuelos;
    }
    @Override
    public VueloResponse getVueloConDetalleByNroVuelo(String numeroVuelo) {
        // Obtenemos el vuelo con el número de vuelo proporcionado desde el repositorio
        VueloResponse vuelo = vueloRepository.findByNumeroVueloWithDetails(numeroVuelo).stream().findFirst().orElse(null);
        if (vuelo == null) {
            throw new RuntimeException("No se encontró el vuelo con el número: " + numeroVuelo);
        }

        // Obtenemos los detalles del vuelo para el vuelo encontrado
        List<DetalleVueloEntity> detallesVuelo = detalleVueloRepository.findByVueloId(vuelo.getVueloId());

        // Mapeamos cada detalle del vuelo a un DetalleVueloResponse y los guardamos en una lista
        List<DetalleVueloResponse> detalleVueloResponses = detallesVuelo.stream()
                .map(detalle -> new DetalleVueloResponse(detalle.getPasajero().getNombre(), detalle.getPasajero().getApellido(), detalle.getNumeroAsiento()))
                .collect(Collectors.toList());

        vuelo.setDetalleVueloResponses(detalleVueloResponses);
        return vuelo;
    }

    @Override
    public List<VueloResponse> getVueloConDetalleByFecha(String fecha) {
        List<VueloResponse> vuelos = vueloRepository.findByFechaVueloWithDetails(fecha);
        if (vuelos.isEmpty()) {
            throw new RuntimeException("No se encontraron vuelos para la fecha: " + fecha);
        }
        for(VueloResponse vueloResponse : vuelos){
            List<DetalleVueloEntity> detallesVuelo = detalleVueloRepository.findByVueloId(vueloResponse.getVueloId());
            List<DetalleVueloResponse> detalleVueloResponses = detallesVuelo.stream()
                    .map(detalle -> new DetalleVueloResponse(detalle.getPasajero().getNombre(), detalle.getPasajero().getApellido(), detalle.getNumeroAsiento()))
                    .collect(Collectors.toList());
            vueloResponse.setDetalleVueloResponses(detalleVueloResponses);
        }
        return vuelos;
    }

    @Override
    @Transactional
    public DetalleVueloResponse createDetalleVuelo(Long vueloId, DetalleVueloRequest detalleVueloRequest) {
        VueloEntity vueloEntity = vueloRepository.findById(vueloId).orElseThrow(() -> new RuntimeException("No se encontró el vuelo con el ID: " + vueloId));
        List<DetalleVueloEntity> detalleVueloEntity = detalleVueloRepository.findByVueloId(vueloId);
        for(DetalleVueloEntity detallesVuelo : detalleVueloEntity){
            if(detallesVuelo.getNumeroAsiento().equals(detalleVueloRequest.getNumeroAsiento())){
                throw new RuntimeException("El asiento: "+detallesVuelo.getNumeroAsiento()+" ya ha sido asignado a "+detallesVuelo.getPasajero().getApellido()+" "+detallesVuelo.getPasajero().getNombre());
            }
            if(detallesVuelo.getPasajero().getId().equals(detalleVueloRequest.getPasajero())){
                throw new RuntimeException("El pasajero ya ha sido asignado al vuelo");
                }
            if(vueloEntity.getAvion().getCapacidad() <= detalleVueloEntity.size()){
                throw new RuntimeException("El avion "+ vueloEntity.getAvion().getTipoAvion().getDescripcion()+" no tiene capacidad para más pasajeros.|CANTIDAD LIMITE AVION: "+vueloEntity.getAvion().getCapacidad());
            }
        }
        PasajeroEntity pasajeroEntity = pasajeroRepository.findById(detalleVueloRequest.getPasajero()).orElseThrow(() -> new RuntimeException("El pasajero no existe"));

        DetalleVueloEntity nuevoDetalleVueloEntity = modelMapper.map(detalleVueloRequest, DetalleVueloEntity.class);
        nuevoDetalleVueloEntity.setPasajero(pasajeroEntity);
        nuevoDetalleVueloEntity.setVuelo(vueloEntity);

        detalleVueloRepository.save(nuevoDetalleVueloEntity);
        vueloEntity.getDetallesVuelos().add(nuevoDetalleVueloEntity);

        vueloRepository.save(vueloEntity);

        return modelMapper.map(nuevoDetalleVueloEntity, DetalleVueloResponse.class);
    }

    private boolean compararOrigenDestino(String origen, String destino){
        boolean bandera = false;
        if(origen.equals(destino)){
            bandera = true;
        }
        return bandera;
    }
}
