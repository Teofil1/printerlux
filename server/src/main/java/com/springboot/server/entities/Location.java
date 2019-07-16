package com.springboot.server.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "firsttwooctetsipaddress", length = 20)
    private String firstTwoOctetsIpAddress;
    @Column(name = "namelocation", length = 100)
    private String nameLocation;

}
