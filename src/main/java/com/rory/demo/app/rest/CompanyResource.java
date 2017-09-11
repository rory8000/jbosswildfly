package com.rory.demo.app.rest;

import com.rory.demo.app.services.CompanyManager;
import com.rory.demo.dtos.CompanyDTO;
import com.rory.demo.pagination.PaginatedListWrapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@ApplicationPath("/resources")
@Path("companies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompanyResource extends Application {

    @Inject
    private CompanyManager companyManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PaginatedListWrapper findAll(@DefaultValue("1")
                                        @QueryParam("page")
                                                Integer page,
                                        @DefaultValue("id")
                                        @QueryParam("sortFields")
                                                String sortFields,
                                        @DefaultValue("asc")
                                        @QueryParam("sortDirections")
                                                String sortDirections) {
        PaginatedListWrapper paginatedListWrapper = new PaginatedListWrapper();
        paginatedListWrapper.setCurrentPage(page);
        paginatedListWrapper.setSortFields(sortFields);
        paginatedListWrapper.setSortDirections(sortDirections);
        paginatedListWrapper.setPageSize(10);
        return findPersons(paginatedListWrapper);
    }

    private PaginatedListWrapper findPersons(PaginatedListWrapper wrapper) {
        wrapper.setTotalResults(companyManager.countCompanies());
        int start = (wrapper.getCurrentPage() - 1) * wrapper.getPageSize();
        wrapper.setList(companyManager.findCompanies(start,
                wrapper.getPageSize(),
                wrapper.getSortFields(),
                wrapper.getSortDirections()));
        return wrapper;
    }

    @GET
    @Path("{id}")
    public CompanyDTO getCompany(@PathParam("id") Long id) {
        return companyManager.findById(id);
    }

    @POST
    public CompanyDTO saveCompany(@Context SecurityContext sc, CompanyDTO company) {
//        if (!sc.isUserInRole("admin")){
//            throw new SecurityException("User is unauthorized.");
//        }
        return companyManager.saveOrUpdate(company);
    }

    @DELETE
    @Path("{id}")
    public void deleteCompany(@Context SecurityContext sc, @PathParam("id") Long id) {
//        if (!sc.isUserInRole("admin")){
//            throw new SecurityException("User is unauthorized.");
//        }
        companyManager.deleteById(id);
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CompanyDTO> findAll(@DefaultValue("")
                                    @QueryParam("text")
                                            String text) {
        return companyManager.findAll(text);
    }

}
