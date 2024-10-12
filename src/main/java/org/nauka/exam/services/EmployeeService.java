package org.nauka.exam.services;

import org.nauka.exam.model.Employee;
import org.nauka.exam.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;

    @Autowired
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Optional<Employee> findById(long id) {
        return repository.findById(id);
    }

    public Map<String, Integer> groupByName() {
        return repository.groupByName();
    }

    public List<Employee> findBetween(LocalDate from, LocalDate to) {
        return repository.findBetween(from, to);
    }
}
