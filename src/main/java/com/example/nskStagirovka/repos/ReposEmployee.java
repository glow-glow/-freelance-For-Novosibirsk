package com.example.nskStagirovka.repos;

import com.example.nskStagirovka.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReposEmployee extends JpaRepository<Employee, UUID> {
}
