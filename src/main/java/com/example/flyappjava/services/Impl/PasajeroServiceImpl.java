package com.example.flyappjava.services.Impl;

import com.example.flyappjava.entities.PasajeroEntity;
import com.example.flyappjava.entities.TipoDocumentoEntity;
import com.example.flyappjava.models.Pasajero;
import com.example.flyappjava.models.TipoDocumento;
import com.example.flyappjava.repositories.PasajeroRepository;
import com.example.flyappjava.repositories.TipoDocumentoRepository;
import com.example.flyappjava.services.PasajeroService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PasajeroServiceImpl implements PasajeroService {

    private final PasajeroRepository pasajeroRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PasajeroServiceImpl(PasajeroRepository pasajeroRepository, ModelMapper modelMapper,TipoDocumentoRepository tipoDocumentoRepository) {
        this.pasajeroRepository = pasajeroRepository;
        this.modelMapper = modelMapper;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    @Override
    public Pasajero createPasajero(Pasajero pasajero) {
        if(pasajero.getTipoDocumento().getId() == null) {
            throw new RuntimeException("El tipo de documento no puede ser nulo");
        }
        PasajeroEntity pasajeroEntity = modelMapper.map(pasajero, PasajeroEntity.class);
        pasajeroEntity.setEstado(true);
        pasajeroEntity = pasajeroRepository.save(pasajeroEntity);
        return modelMapper.map(pasajeroEntity, Pasajero.class);
    }

    @Override
    public List<Pasajero> getAllPasajero() {
        List<PasajeroEntity> pasajeroEntities = pasajeroRepository.findAll();
        return pasajeroEntities.stream().map(pasajeroEntity -> modelMapper.map(pasajeroEntity, Pasajero.class)).toList();
    }

    @Override
    public Pasajero getPasajeroByNroDoc(String numeroDocumento) {
        PasajeroEntity pasajeroEntity = pasajeroRepository.findByNumeroDocumento(numeroDocumento);
        if(pasajeroEntity == null){
            throw new RuntimeException("Pasajero no encontrado");
        }
        return modelMapper.map(pasajeroEntity, Pasajero.class);
    }

    @Override
    public Boolean cambiarEstado(String numeroDocumento) {
        PasajeroEntity pasajeroEntity = pasajeroRepository.findByNumeroDocumento(numeroDocumento);
        if(pasajeroEntity == null){
            throw new RuntimeException("Pasajero no encontrado");
        }
        Boolean estado = pasajeroEntity.getEstado();
        if(estado == null){
            throw new RuntimeException("Estado del pasajero es nulo");
        }
        pasajeroEntity.setEstado(!estado);
        pasajeroRepository.save(pasajeroEntity);
        return pasajeroEntity.getEstado();
    }

    @Override
    public TipoDocumento createTipoDocumento(TipoDocumento tipoDocumento) {
        TipoDocumentoEntity tipoDocumentoEntity = modelMapper.map(tipoDocumento, TipoDocumentoEntity.class);
        tipoDocumentoEntity = tipoDocumentoRepository.save(tipoDocumentoEntity);
        return modelMapper.map(tipoDocumentoEntity, TipoDocumento.class);
    }

    @Override
    public TipoDocumento updateTipoDocumento(TipoDocumento tipoDocumento) {
        Optional<TipoDocumentoEntity> tipoDocumentoEntity = tipoDocumentoRepository.findById(tipoDocumento.getId());
        if(tipoDocumentoEntity.isPresent()){
            TipoDocumentoEntity tipoDocumentoObject = tipoDocumentoEntity.get();
            if(tipoDocumentoObject.getDescripcion().equals(tipoDocumento.getDescripcion())){
                throw new RuntimeException("Mismo tipo de documento: "+tipoDocumentoObject.getDescripcion()+" no puede ser actualizado");
            }
            tipoDocumentoObject.setId(tipoDocumento.getId());
            tipoDocumentoObject.setDescripcion(tipoDocumento.getDescripcion());
            TipoDocumentoEntity tipoDocumentoSaved = tipoDocumentoRepository.save(tipoDocumentoObject);
            return modelMapper.map(tipoDocumentoSaved, TipoDocumento.class);
        }
        else{
            throw new RuntimeException("Tipo de documento no encontrado");
        }

    }

    @Override
    public List<TipoDocumento> getAllTipoDocumento() {
        List<TipoDocumentoEntity> tipoDocumentoEntities = tipoDocumentoRepository.findAll();
        return tipoDocumentoEntities.stream().map(tipoDocumentoEntity -> modelMapper.map(tipoDocumentoEntity, TipoDocumento.class)).toList();
    }
}
