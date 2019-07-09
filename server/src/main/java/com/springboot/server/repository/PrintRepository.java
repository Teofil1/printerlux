package com.springboot.server.repository;


import com.springboot.server.entities.Print;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrintRepository extends JpaRepository<Print, Integer> {

    Print getById(Integer id);

    List<Print> findByOwner(String owner);
    /*@Query("select p from print p where owner = :name")
    List<Print> findByOwner(String name);*/
}
