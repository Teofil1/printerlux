package com.springboot.server.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationDTO {
    private Long id;
    private String firstTwoOctetsIpAddress;
    private String nameLocation;
}
