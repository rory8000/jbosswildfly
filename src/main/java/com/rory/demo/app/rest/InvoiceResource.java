package com.rory.demo.app.rest;

import com.rory.demo.app.services.InvoiceManager;
import com.rory.demo.dtos.InvoiceDTO;
import com.rory.demo.dtos.InvoiveSummaryDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@ApplicationPath("/resources")
@Path("invoices")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InvoiceResource extends Application {

    @Inject
    private InvoiceManager invoiceManager;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void save(@Context SecurityContext sc, InvoiceDTO invoiceDTO) {
        if (!sc.isUserInRole("admin")){
            throw new SecurityException("User is unauthorized.");
        }
        invoiceManager.save(invoiceDTO);
    }

    @GET
    @Path("summary")
    @Produces(MediaType.APPLICATION_JSON)
    public List<InvoiveSummaryDTO> getSummary(){
        return  invoiceManager.getSummary();
    }

}
