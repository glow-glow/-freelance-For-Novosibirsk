package com.example.nskStagirovka.service;

import com.example.nskStagirovka.CSVHelper;
import com.example.nskStagirovka.model.Employee;
import com.example.nskStagirovka.repos.ReposEmployee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ServiceEmployeeImpl implements ServiceEmployee {
    private final ReposEmployee reposEmployee;

    public ServiceEmployeeImpl(ReposEmployee reposEmployee) {
        this.reposEmployee = reposEmployee;
    }

    @Override
    public List<Employee> findAll() {
        return reposEmployee.findAll();
    }

    @Override
    public Employee add(Employee employee) {
        return reposEmployee.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        return reposEmployee.save(employee);
    }

    @Override
    public void deleteById(UUID id) {
        reposEmployee.deleteById(id);
    }

    @Override
    public UUID changeStatus(UUID id) {
        Employee employee = reposEmployee.findById(id).orElseThrow(() -> new EntityNotFoundException());
        employee.setStatus(!employee.getStatus());
        reposEmployee.save(employee);
        return employee.getId();
    }


    public void save(MultipartFile file) {
        try {
            List<Employee> tutorials = CSVHelper.csvToMake(file.getInputStream());
            reposEmployee.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream load() {
        List<Employee> tutorials = reposEmployee.findAll();

        ByteArrayInputStream in = CSVHelper.tutorialsToCSV(tutorials);
        return in;
    }





}
