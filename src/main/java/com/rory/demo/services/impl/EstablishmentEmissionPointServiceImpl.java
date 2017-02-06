package com.rory.demo.services.impl;

import com.rory.demo.entity.Company;
import com.rory.demo.entity.EstablishmentEmissionPoint;
import com.rory.demo.exceptions.EstablishmentEmissionPointAlreadyExistException;
import com.rory.demo.exceptions.InvoiceAlreadyExistException;
import com.rory.demo.repositories.CompanyRepository;
import com.rory.demo.repositories.EstablishmentEmissionPointRepository;
import com.rory.demo.services.CompanyService;
import com.rory.demo.services.EstablishmentEmissionPointService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.List;

@Stateless
public class EstablishmentEmissionPointServiceImpl implements EstablishmentEmissionPointService {

    @Inject
    private EstablishmentEmissionPointRepository establishmentEmissionPointRepository;

    @Override
    public void save(EstablishmentEmissionPoint establishmentEmissionPoint) throws EstablishmentEmissionPointAlreadyExistException {
        checkDuplicate(establishmentEmissionPoint);
        establishmentEmissionPointRepository.persist(establishmentEmissionPoint);
    }

    private void checkDuplicate(EstablishmentEmissionPoint establishmentEmissionPoint) throws EstablishmentEmissionPointAlreadyExistException {
        try {
            establishmentEmissionPointRepository.find(establishmentEmissionPoint.getCompany().getId(), establishmentEmissionPoint.getEstablishment(), establishmentEmissionPoint.getEmissionPoint());
            throw new EstablishmentEmissionPointAlreadyExistException("Establecimiento y punto de emisión ya existente");
        } catch (NoResultException ignored) {

        }
    }
}
