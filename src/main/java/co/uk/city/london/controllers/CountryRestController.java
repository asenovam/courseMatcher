/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.controllers;

import org.springframework.web.bind.annotation.RestController;

import co.uk.city.london.beans.Country;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * @author mar_9
 */
@RestController
public class CountryRestController {
    
    @GetMapping("/countries")
    public Country getCountry(@RequestParam(value = "name", required = true) String name) {
        Country country = new Country();
        country.setCapital("Madrid");
        
        return country;
    }
}
