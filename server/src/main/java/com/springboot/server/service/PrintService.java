package com.springboot.server.service;


import com.springboot.server.entity.Print;
import com.springboot.server.entity.PrintDTO;
import com.springboot.server.repository.PrintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Service
public class PrintService {
    private PrintRepository printRepository;

    @Autowired
    public PrintService(PrintRepository printRepository) {
        this.printRepository = printRepository;
    }

    public Print addPrint(@RequestBody PrintDTO printDTO) {
        Print print = new Print();
        print.setOwner(printDTO.getOwner());
        print.setNameDocument(printDTO.getDocument());
        print.setNumberPages(printDTO.getPagesPrinted());
        print.setDatePrint(LocalDateTime.now().toString());
        return printRepository.save(print);

    }

}
