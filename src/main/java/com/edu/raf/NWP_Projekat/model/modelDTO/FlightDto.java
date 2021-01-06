package com.edu.raf.NWP_Projekat.model.modelDTO;

import com.edu.raf.NWP_Projekat.model.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDto {

    private Long id;
    private City origin;
    private City destination;
    private Collection<TicketDto> tickets;

}
