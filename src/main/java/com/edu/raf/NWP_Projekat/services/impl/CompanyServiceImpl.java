package com.edu.raf.NWP_Projekat.services.impl;

import com.edu.raf.NWP_Projekat.model.Company;
import com.edu.raf.NWP_Projekat.model.Ticket;
import com.edu.raf.NWP_Projekat.model.modelDTO.CompanyDto;
import com.edu.raf.NWP_Projekat.model.modelDTO.FlightDto;
import com.edu.raf.NWP_Projekat.model.modelDTO.TicketDto;
import com.edu.raf.NWP_Projekat.repositories.CompanyRepository;
import com.edu.raf.NWP_Projekat.repositories.ReservationRepository;
import com.edu.raf.NWP_Projekat.services.CompanyService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public boolean deleteCompany(Long id) {
        Optional<Company> optionalCompany = this.companyRepository.findById(id);
        if(optionalCompany.isPresent()){
            reservationRepository.deleteAllByTicket_Company_Id(id);
            this.companyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Company updateCompany(Long id, String name) {
        Company company = this.companyRepository.findById(id).orElseThrow(()->new RuntimeException("Company sa ID-jem " + id + " ne postoji!"));
        if(name != null && !name.isEmpty()){
            company.setName(name);
        }

        if(company != null){
            this.companyRepository.save(company);
            return company;
        }
        return null;
    }

    @Override
    public Company addCompany(Company company) {
        return this.companyRepository.save(company);
    }

    @Override
    public List<Company> searchCompany(String name) {
        return null;
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        List<Company> companies = this.companyRepository.findAll();
        List<CompanyDto> companyDtos = new ArrayList<>();
        for (Company company : companies) {
            companyDtos.add(Company.companyToDto(company));
        }
       return companyDtos;
    }

    @Override
    public CompanyDto getById(Long id) {
        Company company = this.companyRepository.findById(id).orElseThrow(()->new RuntimeException("Company sa ID-jem " + id + " ne postoji!"));
        return Company.companyToDto(company);
    }

    @Override
    public CompanyDto getByName(String name) {
        return Company.companyToDto(companyRepository.getCompanyByName(name));
    }
}
