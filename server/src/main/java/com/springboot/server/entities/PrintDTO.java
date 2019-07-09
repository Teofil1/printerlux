package com.springboot.server.entities;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class PrintDTO {

    private Integer id;
    private String owner;
    private String document;
    private Integer pagesPrinted;
    private String datePrint;


}
