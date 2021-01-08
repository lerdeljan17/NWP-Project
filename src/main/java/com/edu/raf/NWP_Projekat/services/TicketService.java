package com.edu.raf.NWP_Projekat.services;

import com.edu.raf.NWP_Projekat.model.Ticket;
import com.edu.raf.NWP_Projekat.model.modelDTO.TicketDto;
import com.edu.raf.NWP_Projekat.model.modelDTO.TicketResponseDto;

import java.util.List;

public interface TicketService {
    public boolean deleteTicket(Long id);

    public TicketDto updateTicket(Long id, TicketDto newTicket);

    public TicketDto addTicket(TicketDto ticketDto);

    public List<Ticket> searchTicket(String name);

    public List<TicketResponseDto> getAllTickets();

    public TicketDto getById(Long id);

    List<TicketResponseDto> filterTickets(String origin, String destination, String departDate, String returnDate);

    List<TicketResponseDto> filterTicketsCompany(String origin, String destination, String departDate, String returnDate, String comapny);

    List<TicketResponseDto> getAllByOneWayEquals(Boolean oneWay);

    List<TicketResponseDto> getAllByCompanyName(String company);


}
