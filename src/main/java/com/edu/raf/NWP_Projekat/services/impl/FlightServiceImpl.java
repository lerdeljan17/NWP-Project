package com.edu.raf.NWP_Projekat.services.impl;

import com.edu.raf.NWP_Projekat.Exceptions.FllightException;
import com.edu.raf.NWP_Projekat.model.Company;
import com.edu.raf.NWP_Projekat.model.Flight;
import com.edu.raf.NWP_Projekat.model.Ticket;
import com.edu.raf.NWP_Projekat.model.modelDTO.CompanyDto;
import com.edu.raf.NWP_Projekat.model.modelDTO.FlightDto;
import com.edu.raf.NWP_Projekat.model.modelDTO.TicketDto;
import com.edu.raf.NWP_Projekat.repositories.CityRepository;
import com.edu.raf.NWP_Projekat.repositories.FlightRepository;
import com.edu.raf.NWP_Projekat.services.FlightService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public boolean deleteFlight(Long id) {
        Optional<Flight> optionalFlight = this.flightRepository.findById(id);
        if(optionalFlight.isPresent()){
            this.flightRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public FlightDto updateFlight(Long id, FlightDto newFlight) {
        Flight flight = this.flightRepository.findById(id).orElseThrow(()->new RuntimeException("Flight does not exist"));

        if(newFlight.getDestination() != null){
            flight.setDestination(cityRepository.findById(newFlight.getDestination().getId()).get());
        }

        if(newFlight.getOrigin() != null){
            flight.setOrigin(cityRepository.findById(newFlight.getOrigin().getId()).get());
        }

        if(flight != null){
            if(flight.getDestination().getId().equals(flight.getOrigin().getId()))throw new FllightException("origin and destination ca not be samo palce");
            this.flightRepository.save(flight);
            return Flight.flightToDto(flight);
        }

        return null;
    }

    @Override
    public FlightDto addFlight(Flight flight) {
        if(flight.getDestination().getId().equals(flight.getOrigin().getId()))throw new FllightException("origin and destination ca not be samo palce");
        this.flightRepository.save(flight);
        return Flight.flightToDto(flight);
    }

    @Override
    public List<Flight> searchFlight(String name) {
        return null;
    }

    @Override
    public List<FlightDto> getAllFlights() {
        List<Flight> flights = this.flightRepository.findAll();
        List<FlightDto> flightDtos = new ArrayList<>();
        for (Flight flight : flights) {
            flightDtos.add(Flight.flightToDto(flight));
        }
        return flightDtos;
    }

    @Override
    public FlightDto getById(Long id) {
        Flight flight = this.flightRepository.findById(id).orElseThrow(()->new RuntimeException("Flight sa ID-jem " + id + " ne postoji!"));
        return Flight.flightToDto(flight);
    }
}
