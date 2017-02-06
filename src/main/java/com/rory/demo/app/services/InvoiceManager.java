package com.rory.demo.app.services;

import com.rory.demo.app.exceptions.MyCustomException;
import com.rory.demo.dtos.EstablishmentEmissionPointDTO;
import com.rory.demo.dtos.InvoiceDTO;
import com.rory.demo.dtos.InvoiveSummaryDTO;
import com.rory.demo.entity.Company;
import com.rory.demo.entity.EstablishmentEmissionPoint;
import com.rory.demo.entity.Invoice;
import com.rory.demo.exceptions.EstablishmentEmissionPointAlreadyExistException;
import com.rory.demo.exceptions.InvoiceAlreadyExistException;
import com.rory.demo.services.EstablishmentEmissionPointService;
import com.rory.demo.services.InvoiceService;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

public class InvoiceManager {

    @Inject
    private InvoiceService invoiceService;

    @Inject
    private EstablishmentEmissionPointService establishmentEmissionPointService;

    public void save(InvoiceDTO invoiceDTO) {
        try {
            EstablishmentEmissionPoint establishmentEmissionPoint = new EstablishmentEmissionPoint();

            final EstablishmentEmissionPointDTO establishmentEmissionPointDTO = invoiceDTO.getEstablishmentEmissionPoint();
            if (establishmentEmissionPointDTO.getId() == null) {
                Company company = new Company();
                company.setId(establishmentEmissionPointDTO.getCompanyId());
                establishmentEmissionPoint.setCompany(company);
                establishmentEmissionPoint.setEmissionPoint(establishmentEmissionPointDTO.getEmissionPoint());
                establishmentEmissionPoint.setEstablishment(establishmentEmissionPointDTO.getEstablishment());
                establishmentEmissionPointService.save(establishmentEmissionPoint);
            } else {
                establishmentEmissionPoint.setId(establishmentEmissionPointDTO.getId());
            }

            Invoice invoice = new Invoice();
            invoice.setEstablishmentEmissionPoint(establishmentEmissionPoint);
            invoice.setType(invoiceDTO.getType());
            invoice.setNumber(invoiceDTO.getNumber());
            invoice.setValue(invoiceDTO.getValue());


            invoiceService.save(invoice);
        } catch (InvoiceAlreadyExistException | EstablishmentEmissionPointAlreadyExistException e) {
            throw new MyCustomException("Error al registrar el comprobante", e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR, 500);
        }
    }

    public List<InvoiveSummaryDTO> getSummary() {
        return invoiceService.getSummary();
    }
}
