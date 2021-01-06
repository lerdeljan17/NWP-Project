package com.edu.raf.NWP_Projekat.services;

import com.edu.raf.NWP_Projekat.model.City;

import java.util.List;

public interface CityService {

   public boolean deleteCity(Long id);

    public City updateCity(Long id, String name);

    public City addCity(City city);

    public List<City> searchCity(String name);

    public List<City> getAllCities();

    public City getById(Long id);

    public City getByName(String name);

}
