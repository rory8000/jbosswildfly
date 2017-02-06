package com.rory.demo.app.mapper;

import com.rory.demo.dtos.EstablishmentEmissionPointDTO;
import com.rory.demo.entity.EstablishmentEmissionPoint;
import org.hibernate.LazyInitializationException;

import java.util.List;
import java.util.stream.Collectors;

public class EstablishmentEmissionPointMapper implements Mapper<EstablishmentEmissionPoint, EstablishmentEmissionPointDTO> {

    @Override
    public EstablishmentEmissionPointDTO convertToDTO(EstablishmentEmissionPoint establishmentEmissionPoint) {
        EstablishmentEmissionPointDTO establishmentEmissionPointDTO = new EstablishmentEmissionPointDTO();
        establishmentEmissionPointDTO.setId(establishmentEmissionPoint.getId());
        establishmentEmissionPointDTO.setEmissionPoint(establishmentEmissionPoint.getEmissionPoint());
        establishmentEmissionPointDTO.setEstablishment(establishmentEmissionPoint.getEstablishment());
        return establishmentEmissionPointDTO;
    }

    @Override
    public EstablishmentEmissionPoint convertFromDTO(EstablishmentEmissionPointDTO establishmentEmissionPointDTO) {
        return null;
    }

    @Override
    public List<EstablishmentEmissionPointDTO> convertToDTO(List<EstablishmentEmissionPoint> establishmentEmissionPointList) {
        if (establishmentEmissionPointList == null) {
            return null;
        }
        try {
            return establishmentEmissionPointList.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (LazyInitializationException e) {
            return null;
        }
    }

    @Override
    public List<EstablishmentEmissionPoint> convertFromDTO(List<EstablishmentEmissionPointDTO> establishmentEmissionDTOPoint) {
        return null;
    }
}
