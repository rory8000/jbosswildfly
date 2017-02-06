package com.rory.demo.services;

import com.rory.demo.entity.EstablishmentEmissionPoint;
import com.rory.demo.exceptions.EstablishmentEmissionPointAlreadyExistException;

public interface EstablishmentEmissionPointService {

    void save(EstablishmentEmissionPoint establishmentEmissionPoint) throws EstablishmentEmissionPointAlreadyExistException;
}
