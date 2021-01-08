package com.edu.raf.NWP_Projekat.services.impl;

import com.edu.raf.NWP_Projekat.jwt.JwtProvider;
import com.edu.raf.NWP_Projekat.model.*;
import com.edu.raf.NWP_Projekat.model.modelDTO.TicketDto;
import com.edu.raf.NWP_Projekat.model.modelDTO.TicketResponseDto;
import com.edu.raf.NWP_Projekat.model.security.LoginRequest;
import com.edu.raf.NWP_Projekat.repositories.CompanyRepository;
import com.edu.raf.NWP_Projekat.repositories.FlightRepository;
import com.edu.raf.NWP_Projekat.repositories.TicketRepository;
import com.edu.raf.NWP_Projekat.services.ReservationService;
import com.edu.raf.NWP_Projekat.services.TicketService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ReservationService reservationService;


    @Override
    public boolean deleteTicket(Long id) {
        Optional<Ticket> optionalTicket = this.ticketRepository.findById(id);
        if(optionalTicket.isPresent()){
            reservationService.deleteAllByTicket_Id(id);
            this.ticketRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public TicketDto updateTicket(Long id, TicketDto newTicket) {
        Ticket ticket = this.ticketRepository.findById(id).orElseThrow(()->new RuntimeException("Ticket sa ID-jem " + id + " ne postoji!"));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.UK);
        if(newTicket.getDepartDate() != null && !newTicket.getDepartDate().isEmpty()){
            ticket.setDepartDate(LocalDate.parse(newTicket.getDepartDate(), dateTimeFormatter));
        }
        if(newTicket.getReturnDate() != null && !newTicket.getReturnDate().isEmpty()){
            ticket.setReturnDate(LocalDate.parse(newTicket.getReturnDate(), dateTimeFormatter));
        }

        if(newTicket.getCount() != null) ticket.setCount(newTicket.getCount());
        if(newTicket.getOneWay() != null) ticket.setOneWay(newTicket.getOneWay());

        if(newTicket.getFlight() != null){
            Flight flight = this.flightRepository.findById(newTicket.getFlight()).get();
            ticket.setFlight(flight);
        }

        if(newTicket.getCompany() != null){
            Company company = this.companyRepository.findById(newTicket.getCompany()).get();
            ticket.setCompany(company);
        }

        if(ticket != null){
            this.ticketRepository.save(ticket);
            return Ticket.ticketToDto(ticket);
        }

        return null;
    }

    @Override
    public TicketDto addTicket(TicketDto ticketDto) {
        Company company = this.companyRepository.findById(ticketDto.getCompany()).get();
        Flight flight = this.flightRepository.findById(ticketDto.getFlight()).get();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate departureDate = LocalDate.parse(ticketDto.getDepartDate(), dateTimeFormatter);
        LocalDate returnDate = null;
        if(ticketDto.getReturnDate() != null){
            returnDate = LocalDate.parse(ticketDto.getReturnDate(), dateTimeFormatter);
            if(departureDate.isAfter(returnDate)){
                // TODO: 6.1.2021. exeption za tiket koji ima depart posle returna
                System.out.println("exeption za tiket koji ima depart posle returna");
                return null;
            }
        }
        if(ticketDto.getOneWay() == true && (ticketDto.getReturnDate() != null)){
            // TODO: 6.1.2021. exeption za tiket koji je oneway a prosledjen je datum
            System.out.println("exeption za tiket koji je oneway a prosledjen je datum");
            return null;
        }

        if(ticketDto.getOneWay() == false && (ticketDto.getReturnDate() == null)){
            // TODO: 6.1.2021. exeption za tiket koji je two way a nije prosledjen je datum
            System.out.println("exeption za tiket koji je two way a nije prosledjen je datum ");
            return null;
        }

        Ticket ticket = Ticket.builder()
                .id(ticketDto.getId())
                .oneWay(ticketDto.getOneWay())
                .count(ticketDto.getCount())
                .company(company)
                .flight(flight)
                .departDate(departureDate)
                .returnDate(returnDate)
                .build();
        this.ticketRepository.save(ticket);
        return Ticket.ticketToDto(ticket);
    }

    @Override
    public List<Ticket> searchTicket(String name) {

        return null;
    }

    @Override
    public List<TicketResponseDto> getAllTickets() {
        List<Ticket> tickets = this.ticketRepository.findAll();
        List<TicketResponseDto> ticketDtos = new ArrayList<>();
        for (Ticket ticket : tickets) {
            ticketDtos.add(Ticket.ticketToResponseDto(ticket));
        }
        return ticketDtos;

    }

    @Override
    public TicketDto getById(Long id) {
        Ticket ticket = this.ticketRepository.findById(id).orElseThrow(()->new RuntimeException("Ticket sa ID-jem " + id + " ne postoji!"));
        return  Ticket.ticketToDto(ticket);
    }

    @Override
    public List<TicketResponseDto> filterTickets(String origin, String destination,String departDate,String returnDate) {
        List<Ticket> tickets = ticketRepository.findAll();
        List<TicketResponseDto> result = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate departure = null;
        LocalDate arrival = null;
        if(!departDate.isEmpty())
            departure = LocalDate.parse(departDate, formatter);
        if(!returnDate.isEmpty())
            arrival = LocalDate.parse(returnDate,formatter);


        if(arrival!=null && departure!=null) {
            if (arrival.isBefore(departure)) {
                //throw new AirTicketException("departure date must be minimum one day before arrival");
            }
        }
        List<Ticket> toRemove = new ArrayList<>();
        if(origin != null && !origin.equals("") && !origin.equals(" ")) {
            for (Ticket ticket : tickets) {
                if (!ticket.getFlight().getOrigin().getName().equals(origin)) {
                   toRemove.add(ticket);
                }
            }
        }
        tickets.removeAll(toRemove);
        System.out.println(tickets.size());
        toRemove.clear();
        if(destination != null && !destination.equals("") && !destination.equals(" ")) {
            for (Ticket ticket : tickets) {
                if (!ticket.getFlight().getDestination().getName().equals(destination)) {
                    toRemove.add(ticket);
                }
            }
        }
        tickets.removeAll(toRemove);
        System.out.println(tickets.size());
        toRemove.clear();

        if(departure!=null){
            for (Ticket ticket : tickets) {
                if (!ticket.getDepartDate().isAfter(departure)) {
                    toRemove.add(ticket);
                }
            }
        }
        tickets.removeAll(toRemove);
        toRemove.clear();
        if(arrival!=null) {
            for (Ticket ticket : tickets) {
                if (ticket.getReturnDate() == null || !ticket.getReturnDate().isBefore(arrival)) {
                    toRemove.add(ticket);
                }
            }
        }
        tickets.removeAll(toRemove);
        toRemove.clear();

        for (Ticket ticket : tickets) {
            result.add(Ticket.ticketToResponseDto(ticket));
        }
        return result;
    }


    @Override
    public List<TicketResponseDto> filterTicketsCompany(String origin, String destination,String departDate,String returnDate,String company) {
        List<Ticket> tickets = ticketRepository.getAllByCompanyName(company);
        List<TicketResponseDto> result = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate departure = null;
        LocalDate arrival = null;
        if(!departDate.isEmpty())
            departure = LocalDate.parse(departDate, formatter);
        if(!returnDate.isEmpty())
            arrival = LocalDate.parse(returnDate,formatter);


        if(arrival!=null && departure!=null) {
            if (arrival.isBefore(departure)) {
                //throw new AirTicketException("departure date must be minimum one day before arrival");
            }
        }
        List<Ticket> toRemove = new ArrayList<>();
        if(origin != null && !origin.equals("") && !origin.equals(" ")) {
            for (Ticket ticket : tickets) {
                if (!ticket.getFlight().getOrigin().getName().equals(origin)) {
                    toRemove.add(ticket);
                }
            }
        }
        tickets.removeAll(toRemove);
        System.out.println(tickets.size());
        toRemove.clear();
        if(destination != null && !destination.equals("") && !destination.equals(" ")) {
            for (Ticket ticket : tickets) {
                if (!ticket.getFlight().getDestination().getName().equals(destination)) {
                    toRemove.add(ticket);
                }
            }
        }
        tickets.removeAll(toRemove);
        System.out.println(tickets.size());
        toRemove.clear();

        if(departure!=null){
            for (Ticket ticket : tickets) {
                if (!ticket.getDepartDate().isAfter(departure)) {
                    toRemove.add(ticket);
                }
            }
        }
        tickets.removeAll(toRemove);
        toRemove.clear();
        if(arrival!=null) {
            for (Ticket ticket : tickets) {
                if (ticket.getReturnDate() == null || !ticket.getReturnDate().isBefore(arrival)) {
                    toRemove.add(ticket);
                }
            }
        }
        tickets.removeAll(toRemove);
        toRemove.clear();

        for (Ticket ticket : tickets) {
            result.add(Ticket.ticketToResponseDto(ticket));
        }
        return result;
    }

    @Override
    public List<TicketResponseDto> getAllByOneWayEquals(Boolean oneWay) {
        List<Ticket> tickets = this.ticketRepository.getAllByOneWayEquals(oneWay);
        List<TicketResponseDto> ticketDtos = new ArrayList<>();
        for (Ticket ticket : tickets) {
            ticketDtos.add(Ticket.ticketToResponseDto(ticket));
        }
        return ticketDtos;
    }

    @Override
    public List<TicketResponseDto> getAllByCompanyName(String company) {
        List<Ticket> tickets = this.ticketRepository.getAllByCompanyName(company);
        List<TicketResponseDto> ticketDtos = new ArrayList<>();
        for (Ticket ticket : tickets) {
            ticketDtos.add(Ticket.ticketToResponseDto(ticket));
        }
        return ticketDtos;
    }


}
