package com.edu.raf.NWP_Projekat.repositories;

import com.edu.raf.NWP_Projekat.model.Company;
import com.edu.raf.NWP_Projekat.model.modelDTO.CompanyDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company getCompanyByName(String name);
}
