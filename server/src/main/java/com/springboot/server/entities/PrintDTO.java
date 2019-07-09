package com.springboot.server.entities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
public class PrintDTO {
    private Long id;
    private String owner;
    private String nameDocument;
    private Integer numberPages;
    private LocalDateTime datePrint;
}
