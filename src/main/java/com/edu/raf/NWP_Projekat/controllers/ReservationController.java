package com.edu.raf.NWP_Projekat.controllers;

import com.edu.raf.NWP_Projekat.Exceptions.ReservationException;
import com.edu.raf.NWP_Projekat.model.Flight;
import com.edu.raf.NWP_Projekat.model.Ticket;
import com.edu.raf.NWP_Projekat.model.modelDTO.FlightDto;
import com.edu.raf.NWP_Projekat.model.modelDTO.ReservationDto;
import com.edu.raf.NWP_Projekat.model.modelDTO.ReservationsResponse;
import com.edu.raf.NWP_Projekat.model.modelDTO.TicketDto;
import com.edu.raf.NWP_Projekat.services.FlightService;
import com.edu.raf.NWP_Projekat.services.ReservationService;
import com.edu.raf.NWP_Projekat.services.TicketService;
import com.edu.raf.NWP_Projekat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<ReservationDto> getAllReservations(){
        return this.reservationService.getAllReservations();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) throws ReservationException {
        ReservationDto reservationDto = this.reservationService.getById(id);

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

    @GetMapping(value = "/userReservations")
    public List<ReservationsResponse> getById(@RequestParam(name = "username" ,required = true) String username){
        return userService.getUserBookings(username);
    }


    @GetMapping(value = "/buyReservations/{id}")
    public void buyReservations(@PathVariable Long id,@RequestParam(name = "username") String username){
//        System.out.println("------------------------------------------------------------");
//        System.out.println(username);
       reservationService.buyReservations(username,id);
    }


}
