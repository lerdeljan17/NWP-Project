package com.edu.raf.NWP_Projekat.controllers;

import com.edu.raf.NWP_Projekat.model.Flight;
import com.edu.raf.NWP_Projekat.model.modelDTO.FlightDto;
import com.edu.raf.NWP_Projekat.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/all")
    public List<FlightDto> getAllFlights(){
        return this.flightService.getAllFlights();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteFlight(@PathVariable Long id){
        if(this.flightService.deleteFlight(id)){
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<FlightDto> updateFlight(@PathVariable Long id, @RequestBody FlightDto newFlight){
        FlightDto flight = this.flightService.updateFlight(id, newFlight);
        if(flight != null){
            return ResponseEntity.ok(flight);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public FlightDto addFlight(@RequestBody Flight flight){
        return this.flightService.addFlight(flight);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FlightDto> getById(@PathVariable Long id){
        FlightDto flightDto = this.flightService.getById(id);
        if(flightDto != null){
            return ResponseEntity.ok(flightDto);
        }
        return ResponseEntity.notFound().build();
    }

}
