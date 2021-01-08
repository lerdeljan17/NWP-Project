package com.edu.raf.NWP_Projekat.services.impl;

import com.edu.raf.NWP_Projekat.Exceptions.ReservationException;
import com.edu.raf.NWP_Projekat.model.*;
import com.edu.raf.NWP_Projekat.model.modelDTO.ReservationDto;
import com.edu.raf.NWP_Projekat.model.modelDTO.TicketDto;
import com.edu.raf.NWP_Projekat.repositories.FlightRepository;
import com.edu.raf.NWP_Projekat.repositories.ReservationRepository;
import com.edu.raf.NWP_Projekat.repositories.TicketRepository;
import com.edu.raf.NWP_Projekat.repositories.UserRepository;
import com.edu.raf.NWP_Projekat.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
        Optional<Reservation> optionalReservation = this.reservationRepository.findById(id);
        if(optionalReservation.isPresent()){
            TicketDto ticket = Ticket.ticketToDto(this.ticketRepository.findById(optionalReservation.get().getTicket().getId()).get());
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.UK);
            LocalDate departureDate = LocalDate.parse(ticket.getDepartDate(), dateTimeFormatter);
            LocalDate currentDate = LocalDate.now();
            //System.out.println(Duration.between(departureDate.atStartOfDay(), currentDate.atStartOfDay()).toDays());
            //System.out.println(departureDate.compareTo(currentDate));
            if(departureDate.compareTo(currentDate)< 1){
                //TODO Exception
                throw new RuntimeException("can only delete reservation up to 24h before flight departure");
//                System.out.println("can only delete reservation up to 24h before flight departure");
//                return false;
            }
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
        Ticket ticket = this.ticketRepository.findById(reservationDto.getTicket()).orElseThrow(()->new RuntimeException("Ticket does not exist"));
        Flight flight = this.flightRepository.findById(reservationDto.getFlight()).orElseThrow(()->new RuntimeException("Flight does not exist"));
        User user = this.userRepository.findByUsername(reservationDto.getUsername()).orElseThrow(()->new RuntimeException("User does not exist"));;

        if(reservationDto.getCount()<0)throw new ReservationException("Number of cards to reserve can not be negative value or null");

        if(reservationDto.getIsAvailable() == null){
            reservationDto.setIsAvailable(true);
        }

        Reservation reservation = Reservation.builder()
                .id(reservationDto.getId())
                .ticket(ticket)
                .count(reservationDto.getCount())
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

    @Override
    public void buyReservations(String username, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()->new RuntimeException("Reservation does not exist"));
        User user = this.userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User does not exist"));
        Ticket ticket = this.ticketRepository.findById(reservation.getTicket().getId()).orElseThrow(()->new RuntimeException("Ticket does not exist"));

        int count = reservation.getCount();

        if(!reservation.getIsAvailable()){
            // TODO: 8.1.2021. Reservations is not available
            throw new ReservationException("Reservations is not available");
//            return;
        }

        if(ticket.getCount() < count){
            // TODO: 8.1.2021. Exception more cards than abailable
            throw new ReservationException("Exception more cards than available");
//            return;
        }
        reservationRepository.deleteById(reservation.getId());

        ticket.setCount(ticket.getCount()-count);
        reservationRepository.flush();

    }
}
