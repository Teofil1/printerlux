package com.springboot.server.controller;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.profesorfalken.jpowershell.PowerShellResponse;
import com.springboot.server.entity.Print;
import com.springboot.server.repository.PrintRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;


@Controller    // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MainController {
    @Autowired
    private PrintRepository printRepository;

    /*@GetMapping(path="/add")
    public @ResponseBody
    String addNewPrint (@RequestParam String owner, @RequestParam String nameDocument, @RequestParam Integer numberPages
            , @RequestParam String datePrint) {

        printRepository.save(new Print(owner, nameDocument, numberPages, datePrint));
        return "Saved";
    }*/
    @GetMapping(path="/add")
    public @ResponseBody
    void addNewPrint () {

        ArrayList<String> arrayOwners;
        ArrayList<String> arrayDocuments;
        ArrayList<String> arrayPagesPrinted;
        ArrayList<String> arrayTotalPages;

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

                if(numberDocuments>3) {

                    arrayOwners = new ArrayList();
                    arrayDocuments = new ArrayList();
                    arrayPagesPrinted = new ArrayList();
                    arrayTotalPages = new ArrayList();

                    for (int i = 3; i < numberDocuments; i++) {
                        arrayOwners.add(StringUtils.deleteWhitespace(owners.split("\n")[i]));
                    }

                    for (int i = 3; i < numberDocuments; i++) {
                        arrayDocuments.add(StringUtils.deleteWhitespace(documents.split("\n")[i]));
                    }

                    for (int i = 3; i < numberDocuments; i++) {
                        arrayPagesPrinted.add(StringUtils.deleteWhitespace(pagesprinted.split("\n")[i]));
                    }
                    for (int i = 3; i < numberDocuments; i++) {
                        arrayTotalPages.add(StringUtils.deleteWhitespace(totalpages.split("\n")[i]));
                    }

                    for (int i = 0; i < arrayOwners.size(); i++) {
                        if (arrayPagesPrinted.get(i).equals(arrayTotalPages.get(i))) {
                            printRepository.save(new Print(arrayOwners.get(i), arrayDocuments.get(i), arrayPagesPrinted.get(i), LocalDateTime.now().toString()));
                        }
                    }
                    powerShell.executeCommand("Get-WmiObject Win32_PrintJob | Where-Object {$_.JobStatus -eq 'Printed'} | Foreach-Object { $_.Delete() }");
                }
            } catch (PowerShellNotAvailableException ex) {
            }
        }


    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Print> getAllUsers() {
        return printRepository.findAll();
    }
}
