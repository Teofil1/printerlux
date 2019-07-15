package com.springboot.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DataSingleDocumentFromBuffer {
    private final String owner;
    private final String document;
    private final Integer pagesPrinted;
    private final Integer totalPages;
    //private String ipv4;
}