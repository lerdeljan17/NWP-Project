package com.edu.raf.NWP_Projekat.repositories;

import com.edu.raf.NWP_Projekat.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CityRepository extends JpaRepository<City, Long> {
    //@Query(value = "SELECT * FROM city_table c WHERE " + " c.name = :name")
    City findCitiesByNameEquals(@Param("name") String name);
}
