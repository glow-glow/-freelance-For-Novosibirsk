package com.example.nskStagirovka.service;

import com.example.nskStagirovka.model.Employee;

import java.util.List;
import java.util.UUID;

public interface ServiceEmployee {
    List<Employee> findAll();
    Employee add(Employee employee);
    Employee update(Employee employee);
    void deleteById(UUID id);
    UUID changeStatus(UUID id);

}
