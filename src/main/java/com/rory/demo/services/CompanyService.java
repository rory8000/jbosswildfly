package com.rory.demo.services;

import com.rory.demo.entity.Company;

import java.util.List;

public interface CompanyService {

    List<Company> findAll(String text);

    List<Company> findCompanies(int startPosition, Integer maxResults, String sortFields, String sortDirections);

    Integer countCompanies();

    Company findById(Long id);

    void deleteById(Long id);

    void save(Company company);

    Company update(Company company);
}
