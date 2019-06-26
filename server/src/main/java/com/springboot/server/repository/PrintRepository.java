package com.springboot.server.repository;


import com.springboot.server.entities.Print;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrintRepository extends JpaRepository<Print, Integer> {

    Print getById(Integer id);
    //List<Print> findByNameDocument(String nameDocument);
    /*@Query("select p from Print p where owner = :name")
    Print getByName(String name);*/
}
