package com.example.flyappjava.services.Impl;

import com.example.flyappjava.entities.AvionEntity;
import com.example.flyappjava.entities.TipoAvionEntity;
import com.example.flyappjava.models.Avion;
import com.example.flyappjava.models.TipoAvion;
import com.example.flyappjava.repositories.AvionRepository;
import com.example.flyappjava.repositories.TipoAvionRepository;
import com.example.flyappjava.services.AvionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvionServiceImpl implements AvionService {
    private final AvionRepository avionRepository;
    private final TipoAvionRepository tipoAvionRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public AvionServiceImpl(AvionRepository avionRepository, ModelMapper modelMapper, TipoAvionRepository tipoAvionRepository) {
        this.avionRepository = avionRepository;
        this.modelMapper = modelMapper;
        this.tipoAvionRepository = tipoAvionRepository;
    }

    @Override
    public Avion createAvion(Avion avion) {
        if(avion.getTipoAvion().getId() == null) {
            throw new RuntimeException("El tipo de avion no puede ser nulo");
        }
        AvionEntity avionEntity = modelMapper.map(avion, AvionEntity.class);
        avionEntity.setEstado(true);
        avionEntity = avionRepository.save(avionEntity);
        return modelMapper.map(avionEntity, Avion.class);
    }

    @Override
    public TipoAvion createTipoAvion(TipoAvion tipoAvion) {
        Optional<TipoAvionEntity> tipoAviones = tipoAvionRepository.findByDescripcion(tipoAvion.getDescripcion());
        if(tipoAviones.isPresent()){
            throw new RuntimeException("El tipo de avion: "+tipoAvion.getDescripcion()+" ya existe");
        }
        TipoAvionEntity tipoAvionEntity = modelMapper.map(tipoAvion, TipoAvionEntity.class);
        tipoAvionEntity = tipoAvionRepository.save(tipoAvionEntity);
        return modelMapper.map(tipoAvionEntity, TipoAvion.class);
    }

    @Override
    public TipoAvion updateTipoAvion(TipoAvion tipoAvion) {
        Optional <TipoAvionEntity> tipoAvionEntity = tipoAvionRepository.findById(tipoAvion.getId());
        if (tipoAvionEntity.isPresent()) {
            TipoAvionEntity tipoAvionObject = tipoAvionEntity.get();
            if(tipoAvionObject.getDescripcion().equals(tipoAvion.getDescripcion())){
                throw new RuntimeException("Mismo tipo de avion: "+tipoAvionObject.getDescripcion()+" no puede ser actualizado");
            }
            tipoAvionObject.setDescripcion(tipoAvion.getDescripcion());
            TipoAvionEntity tipoAvionSaved = tipoAvionRepository.save(tipoAvionObject);
            return modelMapper.map(tipoAvionSaved, TipoAvion.class);
        }
        else {
            throw new RuntimeException("No se encontro al avion con ID: "+tipoAvion.getId());
        }
    }

    @Override
    public Avion updateAvion(Avion avion) {
        Optional <AvionEntity> avionEntity = avionRepository.findById(avion.getId());
        if (avionEntity.isPresent()) {
            AvionEntity avionObject = avionEntity.get();
            avionObject.setTipoAvion(modelMapper.map(avion.getTipoAvion(), TipoAvionEntity.class));
            avionObject.setCapacidad(avion.getCapacidad());
            avionObject.setMatricula(avion.getMatricula());
            AvionEntity avionSaved = avionRepository.save(avionObject);
            return modelMapper.map(avionSaved, Avion.class);
        }
        else {
            throw new RuntimeException("No se encontro al avion con ID: "+avion.getId());
        }
    }

    @Override
    public TipoAvion deleteTipoAvion(Long id) {
        Optional <TipoAvionEntity> tipoAvionEntity = tipoAvionRepository.findById(id);
        if (tipoAvionEntity.isPresent()) {
            TipoAvionEntity tipoAvionObject = tipoAvionEntity.get();
            tipoAvionRepository.delete(tipoAvionObject);
            return modelMapper.map(tipoAvionObject, TipoAvion.class);
        }
        else {
            throw new RuntimeException("No se encontro al avion con ID: "+id);
        }
    }

    @Override
    public Avion deleteAvionById(Long id) {
        Optional <AvionEntity> avionEntity = avionRepository.findById(id);
        if (avionEntity.isPresent()) {
            AvionEntity avionObject = avionEntity.get();
            avionRepository.delete(avionObject);
            return modelMapper.map(avionObject, Avion.class);
        }
        else {
            throw new RuntimeException("No se encontro al avion con ID: "+id);
        }
    }

    @Override
    public Avion getAvionByMatricula(String matricula) {
        Optional<AvionEntity> avion = avionRepository.findByMatricula(matricula);
        if(avion.isPresent()){
            return modelMapper.map(avion.get(), Avion.class);
        }
        else{
            throw new RuntimeException("No se encontro al avion con matricula: "+matricula);
        }
    }

    @Override
    public List<Avion> getAllAviones() {
        List<AvionEntity> aviones = avionRepository.findAll();
        if(aviones == null){
            throw new RuntimeException("No hay aviones");
        }
        else{
            return modelMapper.map(aviones, List.class);
        }
    }

    @Override
    public List<TipoAvion> getAllTipoAviones() {
        List<TipoAvion> tipoAviones = modelMapper.map(tipoAvionRepository.findAll(), List.class);
        if(tipoAviones == null){
            throw new RuntimeException("No hay tipos de aviones");
        }
        else{
            return tipoAviones;
        }
    }

    @Override
    public List<Avion> getAvionByTipoAvion(Long id) {
        List<AvionEntity> aviones = avionRepository.findByTipoAvionId(id);
        if(aviones == null){
            throw new RuntimeException("No hay aviones");
        }
        else{
            return modelMapper.map(aviones, List.class);
        }
    }

}
