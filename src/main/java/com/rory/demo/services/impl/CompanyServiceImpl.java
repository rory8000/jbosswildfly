package com.rory.demo.services.impl;

import com.rory.demo.entity.Company;
import com.rory.demo.repositories.CompanyRepository;
import com.rory.demo.services.CompanyService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class CompanyServiceImpl implements CompanyService {

    @Inject
    private CompanyRepository companyRepository;

    @Override
    public List<Company> findAll(String text) {
        return companyRepository.findAll(text);
    }

    @Override
    public List<Company> findCompanies(int startPosition, Integer maxResults, String sortFields, String sortDirections) {
        return companyRepository.findCompanies(startPosition, maxResults, sortFields, sortDirections);
    }

    @Override
    public Integer countCompanies() {
        return companyRepository.countCompanies();
    }

    @Override
    public Company findById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        companyRepository.delete(findById(id));
    }

    @Override
    public void save(Company company) {
        companyRepository.persist(company);
    }

    @Override
    public Company update(Company company) {
        return companyRepository.merge(company);
    }
}
