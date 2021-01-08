package com.edu.raf.NWP_Projekat.repositories;

import com.edu.raf.NWP_Projekat.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Transactional
    void deleteAllByTicket_Id(Long ticketID);

    @Transactional
    void deleteAllByTicket_Company_Id(Long id);
}
