package com.springboot.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@NoArgsConstructor
public class PrintModel {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("document")
    private String document;

    @JsonProperty("pagesPrinted")
    private Integer pagesPrinted;

    @JsonProperty("datePrint")
    private String datePrint;

    public PrintModel(String owner, String document, Integer pagesPrinted) {
        this.owner = owner;
        this.document = document;
        this.pagesPrinted = pagesPrinted;
        datePrint = new Date().toString();

    }

}
