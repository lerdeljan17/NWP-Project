package com.edu.raf.NWP_Projekat.controllers;

import com.edu.raf.NWP_Projekat.model.modelDTO.TicketDto;
import com.edu.raf.NWP_Projekat.model.modelDTO.TicketResponseDto;
import com.edu.raf.NWP_Projekat.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/all")
    public List<TicketResponseDto> getAllTickets(@RequestParam(name = "oneWay",required = false)String oneWay){
        if(oneWay == null || oneWay.equals("null")) {
            return this.ticketService.getAllTickets();
        }else if(oneWay.equals("true")){
            return this.ticketService.getAllByOneWayEquals(true);
        }else if(oneWay.equals("false")){
            return this.ticketService.getAllByOneWayEquals(false);
        }
        return null;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id){
        if(this.ticketService.deleteTicket(id)){
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TicketDto> updateTicket(@PathVariable Long id, @RequestBody TicketDto newTicket){
        TicketDto ticket = this.ticketService.updateTicket(id, newTicket);
        if(ticket != null){
            return ResponseEntity.ok(ticket);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public TicketDto addTicket(@RequestBody TicketDto ticketDto){
        return this.ticketService.addTicket(ticketDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TicketDto> getById(@PathVariable Long id){
        TicketDto ticketDto = this.ticketService.getById(id);
        if(ticketDto != null){
            return ResponseEntity.ok(ticketDto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/filterTickets")
    public List<TicketResponseDto> filterTickets(@RequestParam(name = "origin",required = false)String origin,
                                                 @RequestParam(name = "destination",required = false)String destination,
                                                 @RequestParam(name = "departDate",required = false)String departDate,
                                                 @RequestParam(name = "returnDate",required = false)String returnDate) {

        return ticketService.filterTickets(origin,destination,departDate,returnDate);
    }

    @GetMapping(value = "/filterTicketsCompany")
    public List<TicketResponseDto> filterTicketsCompany(@RequestParam(name = "origin",required = false)String origin,
                                                 @RequestParam(name = "destination",required = false)String destination,
                                                 @RequestParam(name = "departDate",required = false)String departDate,
                                                 @RequestParam(name = "returnDate",required = false)String returnDate,
                                                 @RequestParam(name = "company",required = false)String company) {

        return ticketService.filterTicketsCompany(origin,destination,departDate,returnDate,company);
    }

    @GetMapping("/company")
    public List<TicketResponseDto> getAllByCompanyName(@RequestParam(name = "company")String company){
        return ticketService.getAllByCompanyName(company);
    }



}
