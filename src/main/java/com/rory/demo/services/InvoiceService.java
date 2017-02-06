package com.rory.demo.services;

import com.rory.demo.dtos.InvoiveSummaryDTO;
import com.rory.demo.entity.Invoice;
import com.rory.demo.exceptions.InvoiceAlreadyExistException;

import java.util.List;

public interface InvoiceService {

    void save(Invoice invoice) throws InvoiceAlreadyExistException;

    List<InvoiveSummaryDTO> getSummary();
}
