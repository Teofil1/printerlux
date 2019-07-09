package com.springboot.server.service;


import com.springboot.server.entities.Print;
import com.springboot.server.entities.PrintDTO;
import com.springboot.server.repository.PrintRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class PrintService {
    private PrintRepository printRepository;

    @Autowired
    public PrintService(PrintRepository printRepository) {
        this.printRepository = printRepository;
    }

    public List<Print> getAllPrints() {
        return printRepository.findAll();
    }

    public boolean addPrint(@RequestBody PrintDTO printDTO) {
        Print print = new Print();
        //ModelMapper modelMapper = new ModelMapper();
        //Print print = modelMapper.map(printDTO,Print.class);
        //print.setId(printDTO.getId());
        print.setOwner(printDTO.getOwner());
        print.setNameDocument(printDTO.getDocument());
        print.setNumberPages(printDTO.getPagesPrinted());
        //print.setDatePrint(printDTO.getDatePrint());
        print.setDatePrint(printDTO.getDatePrint());
        try{
            printRepository.save(print);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public List<Print> getPrintByOwner(String owner) {
        return printRepository.findByOwner(owner);
    }



}
