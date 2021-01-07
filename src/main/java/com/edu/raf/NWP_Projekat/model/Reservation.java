package com.edu.raf.NWP_Projekat.model;

import com.edu.raf.NWP_Projekat.model.modelDTO.ReservationDto;
import com.edu.raf.NWP_Projekat.model.modelDTO.ReservationsResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "reservation_table")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "BOOLEAN")
    private Boolean isAvailable;

    @Column(nullable = true)
    @Min(0)
    private int count;

    @ManyToOne
    @JoinColumn(name = "FLIGHT_ID", referencedColumnName = "ID", nullable = false)
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
//    @JsonIgnore
    private User user;

// TODO: 02/01/2021 manyTOone?

    @OneToOne
    @JoinColumn(name = "TICKET_ID", referencedColumnName = "ID", nullable = false)
    private Ticket ticket;

    public static ReservationDto reservationToDto(Reservation reservation){
        ReservationDto reservationDto = ReservationDto.builder()
                .id(reservation.getId())
                .isAvailable(reservation.getIsAvailable())
                .ticket(reservation.getTicket().getId())
                .user(reservation.getUser().getId())
                .flight(reservation.getFlight().getId())
                .build();
        return reservationDto;
    }

    public static ReservationsResponse reservationToResponse(Reservation reservation){
        ReservationsResponse reservationsResponse = ReservationsResponse.builder()
                .id(reservation.getId())
                .isAvailable(reservation.getIsAvailable())
                .ticket(reservation.getTicket().getId())
                .user(reservation.getUser().getId())
                .flight(reservation.getFlight().getId())
                .count(reservation.getCount())
                .ticketResponseDto(Ticket.ticketToResponseDto(reservation.getTicket()))
                .build();
        return reservationsResponse;
    }
}
