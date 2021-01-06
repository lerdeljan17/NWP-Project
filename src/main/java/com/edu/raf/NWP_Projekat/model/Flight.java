package com.edu.raf.NWP_Projekat.model;

import com.edu.raf.NWP_Projekat.model.modelDTO.FlightDto;
import com.edu.raf.NWP_Projekat.model.modelDTO.TicketDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "flight_table")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ORIGIN_ID", referencedColumnName = "ID", nullable = false)
    private City origin;

    @OneToOne
    @JoinColumn(name = "DESTINATION_ID", referencedColumnName = "ID", nullable = false)
    private City destination;

    @OneToMany(mappedBy = "flight", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<Ticket> tickets;

    public static FlightDto flightToDto(Flight flight){
        List<TicketDto> ticketDtos = new ArrayList<>();
        if(flight.getTickets() != null) {
            for (Ticket t : flight.getTickets()) {
                ticketDtos.add(Ticket.ticketToDto(t));
            }
        }
        FlightDto flightDto = FlightDto.builder()
                .id(flight.getId())
                .origin(flight.getOrigin())
                .destination(flight.getDestination())
                .build();

        if(!ticketDtos.isEmpty()) flightDto.setTickets(ticketDtos);
        return flightDto;
    }

}
