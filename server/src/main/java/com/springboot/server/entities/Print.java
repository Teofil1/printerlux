package com.springboot.server.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @Column(name ="numberpages")
    private Integer numberPages;
    @Column(name ="dateprint", length = 100)
    private String datePrint;


}
