package com.springboot.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

public class LocationModel {

    @Data
    @Builder
    public class PrintModel {

        @JsonProperty("id")
        private Long id;
        @JsonProperty("firstTwoOctetsIpAddress")
        private String firstTwoOctetsIpAddress;
        @JsonProperty("nameLocation")
        private String nameLocation;

    }
}
