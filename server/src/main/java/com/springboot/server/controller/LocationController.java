package com.springboot.server.controller;

import com.springboot.server.entities.Location;
import com.springboot.server.entities.LocationDTO;
import com.springboot.server.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/printerlux") // This means URL's start with /demo (after Application path)
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/createLocation")
    public void createLocation(@Valid @RequestBody LocationDTO locationDTO, HttpServletRequest httpServletRequest) {
        locationService.createLocation(locationDTO);
    }

    @GetMapping("/location/{firstTwoOctetsIpAddress}")
    public ResponseEntity getPrintByFirstTwoOctetsIpAddress(@PathVariable("firstTwoOctetsIpAddress") String firstTwoOctetsIpAddress) {
        Location location = locationService.getLocationByFirstTwoOctetsIpAddress(firstTwoOctetsIpAddress);
        log.info("Odczytanie lokalizacji: " + location);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }


}
