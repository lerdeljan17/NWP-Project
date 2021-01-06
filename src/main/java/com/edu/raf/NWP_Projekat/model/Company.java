package com.edu.raf.NWP_Projekat.model;

import com.edu.raf.NWP_Projekat.model.modelDTO.CompanyDto;
import com.edu.raf.NWP_Projekat.model.modelDTO.TicketDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.intellij.lang.annotations.Pattern;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company_table")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Pattern(value = "^[A-Za-z0-9]+$")
    @Pattern(regexp="^[A-Za-z0-9]+$",message="comapny name must only contain alphanumreic chars")
    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<Ticket> tickets;



    public static CompanyDto companyToDto(Company company){
        List<TicketDto> ticketDtos = new ArrayList<>();
        for(Ticket t : company.getTickets()){
            ticketDtos.add(Ticket.ticketToDto(t));
        }
        CompanyDto companyDto = CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .build();
        if(!ticketDtos.isEmpty()) companyDto.setTickets(ticketDtos);
        return companyDto;
    }

}
