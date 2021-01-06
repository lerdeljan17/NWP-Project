package com.edu.raf.NWP_Projekat.services.impl;

import com.edu.raf.NWP_Projekat.model.City;
import com.edu.raf.NWP_Projekat.repositories.CityRepository;
import com.edu.raf.NWP_Projekat.services.CityService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> getAllCities() {
        return this.cityRepository.findAll();
    }

    @Override
    public City getById(Long id) {
        return this.cityRepository.findById(id).orElseThrow(()->new RuntimeException("City sa ID-jem " + id + " ne postoji!"));
    }

    @Override
    public City getByName(String name) {
        return cityRepository.findCitiesByNameEquals(name);
    }

    @Override
    public boolean deleteCity(Long id) {
        Optional<City> optionalCity = this.cityRepository.findById(id);
        if(optionalCity.isPresent()){
            this.cityRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public City updateCity(Long id, String name) {
        City city = this.cityRepository.findById(id).orElseThrow(()->new RuntimeException("City sa ID-jem " + id + " ne postoji!"));
        if(name != null && !name.isEmpty()){
            city.setName(name);
        }

        if(city != null){
            this.cityRepository.save(city);
            return city;
        }
        return null;
    }

    @Override
    public City addCity(City city) {
        return this.cityRepository.save(city);
    }

    @Override
    public List<City> searchCity(String name) {
        return null;
    }

}
