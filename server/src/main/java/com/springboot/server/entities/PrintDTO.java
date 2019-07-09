package com.springboot.server.entities;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrintDTO {
    private Integer id;
    private String owner;
    private String nameDocument;
    private Integer numberPages;
    private String datePrint;
}
