package com.springboot.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PrintModel {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("nameDocument")
    private String nameDocument;
    @JsonProperty("numberPages")
    private Integer numberPages;
    @JsonProperty("datePrint")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime datePrint;
    @JsonProperty("location")
    private LocationModel location;

}
