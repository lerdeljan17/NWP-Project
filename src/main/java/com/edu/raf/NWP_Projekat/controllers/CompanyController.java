package com.edu.raf.NWP_Projekat.controllers;

import com.edu.raf.NWP_Projekat.model.Company;
import com.edu.raf.NWP_Projekat.model.modelDTO.CompanyDto;
import com.edu.raf.NWP_Projekat.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/all")
    public List<CompanyDto> getAllCompany(){
        return this.companyService.getAllCompanies();
    }

    @DeleteMapping(value = "/{name}")
    public ResponseEntity<?> deleteCompany(@PathVariable String name){
        CompanyDto companyDto = companyService.getByName(name);
        if(this.companyService.deleteCompany(companyDto.getId())){
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody String name){
        Company company = this.companyService.updateCompany(id, name);
        if(company != null){
            return ResponseEntity.ok(company);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/editCompany/{cName}")
    public ResponseEntity<CompanyDto> editComapny(@PathVariable String cName,@RequestBody String name){
        System.out.println(name);
        System.out.println(cName);
        CompanyDto companyDto = companyService.getByName(cName);
        Company company = this.companyService.updateCompany(companyDto.getId(), name);
        if(company != null){
            return ResponseEntity.ok(Company.companyToDto(company));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Company addCompany(@RequestBody Company company){
        return this.companyService.addCompany(company);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CompanyDto> getById(@PathVariable Long id){
        CompanyDto companyDto = this.companyService.getById(id);
        if(companyDto != null){
            return ResponseEntity.ok(companyDto);
        }
        return ResponseEntity.notFound().build();
    }
}
