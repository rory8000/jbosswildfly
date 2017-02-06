package com.rory.demo.app.mapper;

import com.rory.demo.dtos.CompanyDTO;
import com.rory.demo.entity.Company;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyMapper implements Mapper<Company, CompanyDTO> {

    @Inject
    private EstablishmentEmissionPointMapper establishmentEmissionPointMapper;

    public CompanyDTO convertToDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(company.getId());
        companyDTO.setName(company.getName());
        companyDTO.setRuc(company.getRuc());
        companyDTO.setEstablishmentEmissionPointList(establishmentEmissionPointMapper.convertToDTO(company.getEstablishmentEmissionPointList()));
        return companyDTO;
    }

    public Company convertFromDTO(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setId(companyDTO.getId());
        company.setName(companyDTO.getName());
        company.setRuc(companyDTO.getRuc());
        return company;
    }

    public List<CompanyDTO> convertToDTO(List<Company> t) {
        if (t == null) {
            return null;
        }
        return t.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<Company> convertFromDTO(List<CompanyDTO> u) {
        return null;
    }
}
