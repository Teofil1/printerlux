package com.springboot.server.entities;

import javax.persistence.*;

@Entity
@Table(name = "print")
public class Print {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name ="owner",length = 100)
    private String owner;
    @Column(name ="namedocument",length = 100)
    private String nameDocument;
    @Column(name ="numberpages",length = 100)
    private String numberPages;
    @Column(name ="dateprint",length = 100)
    private String datePrint;

    public Print(){}

    public Print(String owner, String nameDocument, String numberPages, String datePrint){
        this.owner=owner;
        this.nameDocument=nameDocument;
        this.numberPages=numberPages;
        this.datePrint=datePrint;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getNameDocument() {
        return nameDocument;
    }

    public void setNameDocument(String nameDocument) {
        this.nameDocument = nameDocument;
    }

    public String getNumberPages() {
        return numberPages;
    }

    public void setNumberPages(String numberPages) {
        this.numberPages = numberPages;
    }

    public String getDatePrint() {
        return datePrint;
    }

    public void setDatePrint(String datePrint) {
        this.datePrint = datePrint;
    }

    @Override
    public String toString() {
        return "Print[id="+id+", owner="+owner+", nameDocument="+nameDocument+" , numberPages="+numberPages+" , datePrint="+datePrint+"]";
    }

}
