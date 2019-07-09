package com.springboot.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class PrintModel {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("nameDocument")
    private String nameDocument;
    @JsonProperty("numberPages")
    private Integer numberPages;
    @JsonProperty("datePrint")
    private String datePrint;

    public PrintModel(String owner, String nameDocument, Integer numberPages, String datePrint) {
        this.owner = owner;
        this.nameDocument = nameDocument;
        this.numberPages = numberPages;
        this.datePrint = datePrint ;
    }

}
