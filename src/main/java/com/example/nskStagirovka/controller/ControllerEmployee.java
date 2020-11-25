package com.example.nskStagirovka.controller;

import com.example.nskStagirovka.Halper.CSVHelper;
import com.example.nskStagirovka.Halper.ExcelHelper;
import com.example.nskStagirovka.message.MessageExcel;
import com.example.nskStagirovka.message.ResponseMessage;
import com.example.nskStagirovka.model.Employee;
import com.example.nskStagirovka.service.ServiceEmployeeImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.core.io.Resource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
@ComponentScan(basePackages = {"com.example.*"})
public class ControllerEmployee {
    private final ServiceEmployeeImpl serviceEmployee;

    public ControllerEmployee(ServiceEmployeeImpl serviceEmployee) {
        this.serviceEmployee = serviceEmployee;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> findAll(){
        return ResponseEntity.ok(serviceEmployee.findAll());
    }
    @PostMapping("/add")
    public ResponseEntity<Employee> add(@RequestBody Employee employee){
        return  ResponseEntity.ok(serviceEmployee.add(employee));
    }
    @PostMapping("/update")
    public ResponseEntity<Employee> update(@RequestBody Employee employee){
        return  ResponseEntity.ok(serviceEmployee.update(employee));
    }


    @PutMapping("/changStatus")
    public ResponseEntity<UUID> changeStatus(@PathVariable UUID id){
        return ResponseEntity.ok(serviceEmployee.changeStatus(id));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable UUID id){
        try {
            serviceEmployee.deleteById(id);

        }catch (EmptyResultDataAccessException e){
            return new ResponseEntity("id="+id+" not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }



    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                serviceEmployee.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();

                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/employee/download/")
                        .path(file.getOriginalFilename())
                        .toUriString();

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,fileDownloadUri));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message,""));
            }
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,""));
    }

    @GetMapping("/all2")
    public ResponseEntity<List<Employee>> getAllTutorials() {
        try {
            List<Employee> tutorials = serviceEmployee.findAll();

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        InputStreamResource file = new InputStreamResource(serviceEmployee.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }


    @PostMapping("/uploadexcel")
    public ResponseEntity<MessageExcel> uploadFileExle(@RequestParam("file") MultipartFile file) {
        String messageExcel = "";

        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                serviceEmployee.saveExcel(file);

                messageExcel = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new MessageExcel(messageExcel));
            } catch (Exception e) {
                messageExcel = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageExcel(messageExcel));
            }
        }

        messageExcel = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageExcel(messageExcel));
    }






    }
