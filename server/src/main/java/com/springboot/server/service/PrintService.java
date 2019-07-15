package com.springboot.server.service;

import com.springboot.server.entities.Location;
import com.springboot.server.entities.LocationDTO;
import com.springboot.server.entities.Print;
import com.springboot.server.entities.PrintDTO;
import com.springboot.server.repository.PrintRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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

    public void addPrint(@RequestBody PrintDTO printDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Print print = modelMapper.map(printDTO, Print.class);
        printRepository.save(print);
    }

    public List<Print> getPrintByOwner(String owner) {
        return printRepository.findByOwner(owner);
    }


}
