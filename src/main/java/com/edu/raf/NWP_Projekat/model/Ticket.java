package com.edu.raf.NWP_Projekat.model;

import com.edu.raf.NWP_Projekat.model.modelDTO.TicketDto;
import com.edu.raf.NWP_Projekat.model.modelDTO.TicketResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ticket_table")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //columnDefinition = "BOOLEAN"
    @Column(nullable = false, columnDefinition = "BOOLEAN")
    private Boolean oneWay;

    @Column(nullable = false)
    private LocalDate departDate;

    @Column(nullable = true)
    private LocalDate returnDate;

    @Column(nullable = false)
    @Min(0)
    private Long count;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "ID", nullable = false)
    private Company company;

    @ManyToOne
    //   @JsonIgnore
    @JoinColumn(name = "FLIGHT_ID", referencedColumnName = "ID", nullable = false)
    private Flight flight;

    public static TicketDto ticketToDto(Ticket ticket){
        TicketDto ticketDto = new TicketDto();

        ticketDto.setId(ticket.getId());
        ticketDto.setOneWay(ticket.getOneWay());
        ticketDto.setDepartDate(ticket.getDepartDate().toString());
        if (!(ticket.getReturnDate() == null))
            ticketDto.setReturnDate(ticket.getReturnDate().toString());
        ticketDto.setCount(ticket.getCount());
        ticketDto.setCompany(ticket.getCompany().getId());
        ticketDto.setFlight(ticket.getFlight().getId());

        return ticketDto;
    }


    public static TicketResponseDto ticketToResponseDto(Ticket ticket){
        String returnDate = "";
        if(ticket.getReturnDate() != null) returnDate = ticket.getReturnDate().toString();
        TicketResponseDto ticketResponseDto = TicketResponseDto.builder()
                .id(ticket.getId())
                .company(ticket.getCompany().getName())
                .count(ticket.getCount())
                .oneWay(ticket.getOneWay())
                .departDate(ticket.getDepartDate().toString())
                .returnDate(returnDate)
                .origin(ticket.getFlight().getOrigin().getName())
                .destination(ticket.getFlight().getDestination().getName())
                .flight(ticket.getFlight().getId())
                .build();
        return ticketResponseDto;
    }
}
