package com.springboot.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class LocationModel {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("firstTwoOctetsIpAddress")
    private String firstTwoOctetsIpAddress;
    @JsonProperty("nameLocation")
    private String nameLocation;

}
