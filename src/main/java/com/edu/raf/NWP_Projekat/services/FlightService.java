package com.edu.raf.NWP_Projekat.services;

import com.edu.raf.NWP_Projekat.model.Flight;
import com.edu.raf.NWP_Projekat.model.modelDTO.FlightDto;

import java.util.List;

public interface FlightService {

    public boolean deleteFlight(Long id);

    public  FlightDto updateFlight(Long id, FlightDto newFlight);

    public FlightDto addFlight(Flight flight);

    public List<Flight> searchFlight(String name);

    public List<FlightDto> getAllFlights();

    public FlightDto getById(Long id);
}
