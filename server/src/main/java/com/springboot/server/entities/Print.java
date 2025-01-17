package com.springboot.server.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "print")
public class Print {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "owner", length = 100)
    private String owner;
    @Column(name = "namedocument", length = 100)
    private String nameDocument;
    @Column(name = "numberpages")
    private Integer numberPages;
    @Column(name = "dateprint")
    private LocalDateTime datePrint;

    @ManyToOne
    @JoinColumn(name = "id_location")
    private Location location;

}
