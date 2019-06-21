package com.springboot.server.repository;


import com.springboot.server.entity.Print;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrintRepository extends CrudRepository<Print, Integer> {

    List<Print> findByNameDocument(String nameDocument);
    /*@Query("select p from Print p where owner = :name")
    Print getByName(String name);*/
}
