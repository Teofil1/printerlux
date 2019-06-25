package com.springboot.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PrintModel {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("document")
    private String document;

    @JsonProperty("pagesPrinted")
    private String pagesPrinted;

    @JsonProperty("totalPages")
    private String totalPages;

    public PrintModel(String owner, String document, String pagesPrinted, String totalPages) {
        this.owner = owner;
        this.document = document;
        this.pagesPrinted = pagesPrinted;
        this.totalPages = totalPages;
    }

}
