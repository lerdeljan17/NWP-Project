package com.edu.raf.NWP_Projekat.services;

import com.edu.raf.NWP_Projekat.Exceptions.ReservationException;
import com.edu.raf.NWP_Projekat.model.Reservation;
import com.edu.raf.NWP_Projekat.model.modelDTO.ReservationDto;

import java.util.List;

public interface ReservationService {
    public boolean deleteReservation(Long id)throws ReservationException;

    public ReservationDto updateReservation(Long id, ReservationDto newReservation);

    public ReservationDto addReservation(ReservationDto reservationDto);

    public List<Reservation> searchReservation(String name);

    public List<ReservationDto> getAllReservations();

    public ReservationDto getById(Long id);

    public void deleteAllByTicket_Id(Long id);

    public void buyReservations(String username,Long reservationId) throws ReservationException;
}
