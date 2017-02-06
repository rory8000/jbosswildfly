package com.rory.demo.app.services;

import com.rory.demo.app.exceptions.MyCustomException;
import com.rory.demo.app.mapper.CompanyMapper;
import com.rory.demo.dtos.CompanyDTO;
import com.rory.demo.entity.Company;
import com.rory.demo.services.CompanyService;
import org.hibernate.exception.ConstraintViolationException;

import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

public class CompanyManager {

    @Inject
    private CompanyService companyService;

    @Inject
    private CompanyMapper companyMapper;

    public List<CompanyDTO> findCompanies(int startPosition, Integer maxResults, String sortFields, String sortDirections) {
        final List<Company> companyList = companyService.findCompanies(startPosition, maxResults, sortFields, sortDirections);
        return companyMapper.convertToDTO(companyList);
    }

    public Integer countCompanies() {
        return companyService.countCompanies();
    }

    public CompanyDTO findById(Long id) {
        final Company company = companyService.findById(id);
        return companyMapper.convertToDTO(company);
    }

    public CompanyDTO saveOrUpdate(CompanyDTO companyDTO) {
        try {
            Company company = companyMapper.convertFromDTO(companyDTO);
            if (companyDTO.getId() == null) {
                companyService.save(company);
            } else {
                company = companyService.update(company);
            }
            return companyMapper.convertToDTO(company);
        } catch (EJBTransactionRolledbackException e) {
            if (e.getCause() != null && e.getCause().getCause() != null && e.getCause().getCause().getCause() != null && e.getCause().getCause().getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException psqlException = (ConstraintViolationException) e.getCause().getCause().getCause();
                throw new MyCustomException("Error al registrar el comprobante", psqlException.getSQLException().getMessage(), Response.Status.INTERNAL_SERVER_ERROR, 500);
            }
            throw new MyCustomException("Error al registrar el comprobante", e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR, 500);
        }
    }

    public void deleteById(Long id) {
        companyService.deleteById(id);
    }

    public List<CompanyDTO> findAll(String text) {
        final List<Company> companyList = companyService.findAll(text);
        return companyMapper.convertToDTO(companyList);
    }

}
