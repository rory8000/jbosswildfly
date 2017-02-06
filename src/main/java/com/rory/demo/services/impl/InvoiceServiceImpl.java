package com.rory.demo.services.impl;

import com.rory.demo.dtos.InvoiveSummaryDTO;
import com.rory.demo.entity.Invoice;
import com.rory.demo.exceptions.InvoiceAlreadyExistException;
import com.rory.demo.repositories.InvoiceRepository;
import com.rory.demo.services.InvoiceService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.List;

@Stateless
public class InvoiceServiceImpl implements InvoiceService {

    @Inject
    private InvoiceRepository invoiceRepository;

    @Override
    public void save(Invoice invoice) throws InvoiceAlreadyExistException {
        if (invoice.getNumber() != null) {
            invoice.setNumber(String.format("%09d", Integer.parseInt(invoice.getNumber())));
            checkDuplicate(invoice);
        }
        invoiceRepository.persist(invoice);
    }

    @Override
    public List<InvoiveSummaryDTO> getSummary() {
        return invoiceRepository.getSummary();
    }

    private void checkDuplicate(Invoice invoice) throws InvoiceAlreadyExistException {
        try {
            invoiceRepository.find(invoice.getEstablishmentEmissionPoint().getId(), invoice.getNumber());
            throw new InvoiceAlreadyExistException("Número de comprobante ya existe");
        } catch (NoResultException ignored) {

        }
    }
}
