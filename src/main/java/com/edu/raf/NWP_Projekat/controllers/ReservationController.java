package com.edu.raf.NWP_Projekat.controllers;

import com.edu.raf.NWP_Projekat.model.modelDTO.FlightDto;
import com.edu.raf.NWP_Projekat.model.modelDTO.ReservationDto;
import com.edu.raf.NWP_Projekat.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/all")
    public List<ReservationDto> getAllReservations(){
        return this.reservationService.getAllReservations();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id){
        if(this.reservationService.deleteReservation(id)){
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ReservationDto> updateReservation(@PathVariable Long id, @RequestBody ReservationDto newReservation){
        ReservationDto reservation = this.reservationService.updateReservation(id, newReservation);
        if(reservation != null){
            return ResponseEntity.ok(reservation);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ReservationDto addReservation(@RequestBody ReservationDto reservation){
        return this.reservationService.addReservation(reservation);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationDto> getById(@PathVariable Long id){
        ReservationDto reservationDto = this.reservationService.getById(id);
        if(reservationDto != null){
            return ResponseEntity.ok(reservationDto);
        }
        return ResponseEntity.notFound().build();
    }


}
