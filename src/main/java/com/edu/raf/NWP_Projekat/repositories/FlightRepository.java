package com.edu.raf.NWP_Projekat.repositories;

import com.edu.raf.NWP_Projekat.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
