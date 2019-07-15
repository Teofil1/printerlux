package com.springboot.server.entities;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class LocationDTO {
    private Long id;
    private String firstTwoOctetsIpAddress;
    private String nameLocation;
   // private Set<Print> prints = new HashSet<Print>();
}
