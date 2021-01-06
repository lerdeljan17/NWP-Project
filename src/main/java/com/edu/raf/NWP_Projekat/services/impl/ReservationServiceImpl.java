package com.edu.raf.NWP_Projekat.services.impl;

import com.edu.raf.NWP_Projekat.model.*;
import com.edu.raf.NWP_Projekat.model.modelDTO.ReservationDto;
import com.edu.raf.NWP_Projekat.repositories.FlightRepository;
import com.edu.raf.NWP_Projekat.repositories.ReservationRepository;
import com.edu.raf.NWP_Projekat.repositories.TicketRepository;
import com.edu.raf.NWP_Projekat.repositories.UserRepository;
import com.edu.raf.NWP_Projekat.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public boolean deleteReservation(Long id) {
        Optional<Reservation> optionalTicket = this.reservationRepository.findById(id);
        if(optionalTicket.isPresent()){
            this.reservationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public ReservationDto updateReservation(Long id, ReservationDto newReservation) {
        Reservation reservation = this.reservationRepository.findById(id).orElseThrow(()->new RuntimeException("Reservation sa ID-jem " + id + " ne postoji!"));

        if(newReservation.getIsAvailable() != null) reservation.setIsAvailable(newReservation.getIsAvailable());

        if(newReservation.getUser() != null){
            User user = this.userRepository.findById(newReservation.getUser()).get();
            reservation.setUser(user);
        }
        if(newReservation.getFlight() != null){
            Flight flight = this.flightRepository.findById(newReservation.getFlight()).get();
            reservation.setFlight(flight);
        }
        if(newReservation.getTicket() != null){
            Ticket ticket = this.ticketRepository.findById(newReservation.getTicket()).get();
            reservation.setTicket(ticket);
        }

        if(reservation != null){
            this.reservationRepository.save(reservation);

            ReservationDto reservationDto = ReservationDto.builder()
                    .id(reservation.getId())
                    .isAvailable(reservation.getIsAvailable())
                    .ticket(reservation.getTicket().getId())
                    .user(reservation.getUser().getId())
                    .flight(reservation.getFlight().getId())
                    .build();
            return reservationDto;
        }

        return null;
    }

    @Override
    public ReservationDto addReservation(ReservationDto reservationDto) {
        Ticket ticket = this.ticketRepository.findById(reservationDto.getTicket()).orElseThrow(()->new RuntimeException("Ticket sa ID-jem " + reservationDto.getTicket() + " ne postoji!"));
        Flight flight = this.flightRepository.findById(reservationDto.getFlight()).orElseThrow(()->new RuntimeException("Flight sa ID-jem " + reservationDto.getFlight() + " ne postoji!"));
        User user = this.userRepository.findById(reservationDto.getUser()).orElseThrow(()->new RuntimeException("User sa ID-jem " + reservationDto.getUser() + " ne postoji!"));;

        //TODO Da li su ovde neophodne provere?
        if(reservationDto.getIsAvailable() == null){
            reservationDto.setIsAvailable(true);
        }

        Reservation reservation = Reservation.builder()
                .id(reservationDto.getId())
                .ticket(ticket)
                .flight(flight)
                .user(user)
                .isAvailable(reservationDto.getIsAvailable())
                .build();

        if(this.reservationRepository.save(reservation) != null){
            return reservationDto;
        }

        return null;
    }

    @Override
    public List<Reservation> searchReservation(String name) {
        return null;
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        List<Reservation> reservations = this.reservationRepository.findAll();
        List<ReservationDto> reservationDtos = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationDtos.add(Reservation.reservationToDto(reservation));
        }
        return reservationDtos;
    }

    @Override
    public ReservationDto getById(Long id) {
        Reservation reservation = this.reservationRepository.findById(id).orElseThrow(()->new RuntimeException("Reservation sa ID-jem " + id + " ne postoji!"));
        return Reservation.reservationToDto(reservation);
    }

    @Override
    public void deleteAllByTicket_Id(Long id) {
        reservationRepository.deleteAllByTicket_Id(id);
    }
}
