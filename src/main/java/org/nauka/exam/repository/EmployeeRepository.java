package org.nauka.exam.repository;

import org.nauka.exam.model.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeRepository {
    Optional<Employee> findById(long id);

    Map<String, Integer> groupByName();

    List<Employee> findBetween(LocalDate from, LocalDate to);
}
