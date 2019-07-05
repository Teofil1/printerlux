package com.springboot.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime datePrint;

    public PrintModel(String owner, String document, Integer pagesPrinted) {
        this.owner = owner;
        this.document = document;
        this.pagesPrinted = pagesPrinted;
        datePrint = LocalDateTime.now();
    }

}
