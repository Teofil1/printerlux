package com.springboot.server.service;


import com.springboot.server.entities.Location;
import com.springboot.server.entities.LocationDTO;
import com.springboot.server.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Slf4j
@Service
public class LocationService {

    private LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location getLocationByFirstTwoOctetsIpAddress(String firstTwoOctetsIpAddress) {
        return locationRepository.getByFirstTwoOctetsIpAddress(firstTwoOctetsIpAddress);
    }

    public void createLocation(@RequestBody LocationDTO locationDTO) {
        //Location location = getLocationByFirstTwoOctetsIpAddress(locationDTO.getFirstTwoOctetsIpAddress());
        //if (location == null) {
            ModelMapper modelMapper = new ModelMapper();
            Location location = modelMapper.map(locationDTO, Location.class);
            locationRepository.save(location);
        //}
        locationDTO.setId(location.getId());
        locationDTO.setNameLocation(location.getNameLocation());
        log.info("!!!!!" + locationDTO.toString());
        //return locationDTO;
    }

}
