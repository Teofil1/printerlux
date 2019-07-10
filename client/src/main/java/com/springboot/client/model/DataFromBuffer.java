package com.springboot.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class DataFromBuffer {
    private String owner;
    private String document;
    private Integer pagesPrinted;
    private Integer totalPages;
    //private String ipv4;
}