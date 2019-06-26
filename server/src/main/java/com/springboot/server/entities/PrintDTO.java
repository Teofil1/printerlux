package com.springboot.server.entities;

public class PrintDTO {

    private String owner;
    private String document;
    private String pagesPrinted;
    private String totalPages;

    public PrintDTO(String owner, String document, String pagesPrinted, String totalPages) {
        this.owner = owner;
        this.document = document;
        this.pagesPrinted = pagesPrinted;
        this.totalPages = totalPages;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getPagesPrinted() {
        return pagesPrinted;
    }

    public void setPagesPrinted(String pagesPrinted) {
        this.pagesPrinted = pagesPrinted;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }
}
