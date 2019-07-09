package com.springboot.server.controller;

import com.springboot.server.entities.Print;
import com.springboot.server.entities.PrintDTO;
import com.springboot.server.service.PrintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/printerlux") // This means URL's start with /demo (after Application path)
public class MainController {

    @Autowired
    private PrintService printService;

    @PostMapping("/addPrint")
    public void addPrint(@Valid @RequestBody PrintDTO printDTO, HttpServletRequest httpServletRequest) {
        log.info("Dodanie wydruku: " + printDTO);
        //PrintDTO print = printService.addPrint(printDTO);
        printService.addPrint(printDTO);
    }

    @GetMapping("/print")
    public ResponseEntity<List<Print>> getCourses(HttpServletRequest httpServletRequest) {
        List<Print> listOfPrints = printService.getAllPrints();
        log.info("Pobranie wydrokow: {}" + listOfPrints.toString());
        return new ResponseEntity<>(listOfPrints, HttpStatus.OK);
    }

    @GetMapping("/print/{owner}")
    public ResponseEntity<List<Print>> getPrintByOwner(@PathVariable("owner") String owner) {
        List<Print> listOfPrints = printService.getPrintByOwner(owner);
        log.info("Pobranie wydrokow: {}" + listOfPrints.toString());
        return new ResponseEntity<>(listOfPrints, HttpStatus.OK);
    }

    @GetMapping("/print2")
    public ResponseEntity<List<Print>> getPrintByOwner2(@RequestParam("owner") String owner) {
        List<Print> listOfPrints = printService.getPrintByOwner(owner);
        log.info("Pobranie wydrokow: {}" + listOfPrints.toString());
        return new ResponseEntity<>(listOfPrints, HttpStatus.OK);
    }

   /* @GetMapping(path="/all")
    public @ResponseBody Iterable<Print> getAllUsers() {
        return printRepository.findAll();
    }*/
}
