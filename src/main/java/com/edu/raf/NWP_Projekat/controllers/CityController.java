package com.edu.raf.NWP_Projekat.controllers;

import com.edu.raf.NWP_Projekat.model.City;
import com.edu.raf.NWP_Projekat.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@CrossOrigin
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/all")
    public List<City> getAllCities(){
        return this.cityService.getAllCities();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Long id){
        if(this.cityService.deleteCity(id)){
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<City> updateCity(@PathVariable Long id, @RequestBody String name){
        City city = this.cityService.updateCity(id, name);
        if(city != null){
            return ResponseEntity.ok(city);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public City addCity(@RequestBody City city){
        return this.cityService.addCity(city);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<City> getById(@PathVariable Long id){
        City city = this.cityService.getById(id);
        if(city != null){
            return ResponseEntity.ok(city);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/byName")
    public ResponseEntity<City> getById(@RequestParam(value = "name") String name){
        City city = this.cityService.getByName(name);
        if(city != null){
            return ResponseEntity.ok(city);
        }
        return ResponseEntity.notFound().build();
    }


}
