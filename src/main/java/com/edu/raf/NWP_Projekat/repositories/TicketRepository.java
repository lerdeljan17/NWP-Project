package com.edu.raf.NWP_Projekat.repositories;

import com.edu.raf.NWP_Projekat.model.Ticket;
import com.edu.raf.NWP_Projekat.model.modelDTO.TicketResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
/*
    @Query(value = "SELECT * FROM ticket_table t JOIN city_table c ON t.origin.id  " + )
    List<Ticket> findBySearchTerm(@Param("start") String origin, @Param("finish") String destination);
*/

    List<Ticket> getAllByOneWayEquals(boolean oneWay);

    List<Ticket> getAllByCompanyName(String company);

}
