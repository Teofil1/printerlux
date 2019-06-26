package com.springboot.server.controller;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.profesorfalken.jpowershell.PowerShellResponse;
import com.springboot.server.entities.Print;
import com.springboot.server.entities.PrintDTO;
import com.springboot.server.service.PrintService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path="/printerlux") // This means URL's start with /demo (after Application path)
public class MainController {

    @Autowired
    private PrintService printService;

    /*@GetMapping("/print")
    void addNewPrint () {

        ArrayList<PrintDTO> arrayPrintDTO;

        while(true) {

            try (PowerShell powerShell = PowerShell.openSession()) {

                PowerShellResponse response;

                response = powerShell.executeCommand("get-wmiobject -class win32_PrintJob | Select-Object Owner");
                String owners = response.getCommandOutput();
                response = powerShell.executeCommand("get-wmiobject -class win32_PrintJob | Select-Object Document");
                String documents = response.getCommandOutput();
                response = powerShell.executeCommand("get-wmiobject -class win32_PrintJob | Select-Object PagesPrinted");
                String pagesprinted = response.getCommandOutput();
                response = powerShell.executeCommand("get-wmiobject -class win32_PrintJob | Select-Object TotalPages");
                String totalpages = response.getCommandOutput();

                int numberDocuments = documents.split("\n").length;
                arrayPrintDTO = new ArrayList<>();

                if(numberDocuments>3) {
                    for (int i = 3; i < numberDocuments; i++) {
                        arrayPrintDTO.add(new PrintDTO(StringUtils.deleteWhitespace(owners.split("\n")[i]), StringUtils.deleteWhitespace(documents.split("\n")[i]),
                                StringUtils.deleteWhitespace(pagesprinted.split("\n")[i]), StringUtils.deleteWhitespace(totalpages.split("\n")[i])));
                    }
                    for (PrintDTO o: arrayPrintDTO) {
                        if(o.getTotalPages().equals(o.getPagesPrinted())){
                            //log.info("Dodanie wydruku: " + o);
                            printService.addPrint(o);
                        }
                    }
                    powerShell.executeCommand("Get-WmiObject Win32_PrintJob | Where-Object {$_.JobStatus -eq 'Printed'} | Foreach-Object { $_.Delete() }");
                }
            } catch (PowerShellNotAvailableException ex) {
            }
        }


    }*/

    @PostMapping("/addPrint")
    public ResponseEntity addPrint(@Valid @RequestBody PrintDTO printDTO, HttpServletRequest httpServletRequest) {
        //log.info("USER: ", httpServletRequest.getRemoteUser());
        log.info("Dodanie kursu: " + printDTO);
        Print print = printService.addPrint(printDTO);
        return new ResponseEntity<>(print, HttpStatus.OK);
    }

    @GetMapping("/print")
    public ResponseEntity<List<Print>> getCourses(HttpServletRequest httpServletRequest) {
        List<Print> listOfPrints = printService.getAllPrints();
        //log.info("USER: ", httpServletRequest.getRemoteUser());
        log.info("Pobranie wydrokow: {}" + listOfPrints.toString());
        return new ResponseEntity<>(listOfPrints, HttpStatus.OK);
    }

   /* @GetMapping(path="/all")
    public @ResponseBody Iterable<Print> getAllUsers() {
        return printRepository.findAll();
    }*/
}
