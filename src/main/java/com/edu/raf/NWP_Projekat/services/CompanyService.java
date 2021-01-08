package com.edu.raf.NWP_Projekat.services;

import com.edu.raf.NWP_Projekat.model.Company;
import com.edu.raf.NWP_Projekat.model.modelDTO.CompanyDto;

import java.util.List;

public interface CompanyService {

    public boolean deleteCompany(Long id);

    public Company updateCompany(Long id, String name);

    public Company addCompany(Company company);

    public List<Company> searchCompany(String name);

    public  List<CompanyDto> getAllCompanies();

    public CompanyDto getById(Long id);

    public CompanyDto getByName(String name);

}
