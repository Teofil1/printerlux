package com.springboot.server.repository;

import com.springboot.server.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    Location getById(Long id);

    Location getByFirstTwoOctetsIpAddress (String firstTwoOctetsIpAddress);
}
