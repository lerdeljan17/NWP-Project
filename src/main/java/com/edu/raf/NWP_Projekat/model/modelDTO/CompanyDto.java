package com.edu.raf.NWP_Projekat.model.modelDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private Long id;
    private String name;
    private Collection<TicketDto> tickets;
}
