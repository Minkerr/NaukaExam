package org.nauka.exam.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.nauka.exam.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmployeeServiceTest {
    private final EmployeeService service;

    @Autowired
    public EmployeeServiceTest(EmployeeService service) {
        this.service = service;
    }

    @ParameterizedTest
    @MethodSource("getSomeEmployees")
    void findByIdTest(int id, Optional<Employee> employee) {
        assertEquals(service.findById(id), employee);
    }

    @Test
    void groupByNameTest() {
        var expected = Map.of("Mike", 2, "John", 2, "James", 1, "Jordan", 1);
        var act = service.groupByName();
        assertEquals(act, expected);
    }

    @Test
    void findBetweenTest() {
        var expected = getSomeEmployees()
                .limit(3)
                .map(x -> ((Optional<Employee>) x.get()[1]).get())
                .toList();

        var act = service.findBetween(
                LocalDate.of(1990, 1, 1),
                LocalDate.of(1992, 12, 31)
        );

        assertEquals(act, expected);
    }

    @Test
    void findBetweenWithemptyResultTest() {
        var expected = List.of();

        var act = service.findBetween(
                LocalDate.of(1980, 1, 1),
                LocalDate.of(1981, 12, 31)
        );

        assertEquals(act, expected);
    }

    private static Stream<Arguments> getSomeEmployees() {
        var e1 = Optional.of(new Employee(1,
                "John",
                "Petrucci",
                LocalDate.of(1990, 1, 15),
                "HR",
                50000.00
        ));
        var e2 = Optional.of(new Employee(3,
                "Mike",
                "Portnoy",
                LocalDate.of(1992, 8, 10),
                "Finance",
                55000.00
        ));
        var e3 = Optional.of(new Employee(6,
                "Jordan",
                "Rudess",
                LocalDate.of(1992, 8, 12),
                "Finance",
                76000.00
        ));
        return Stream.of(
                Arguments.of(1, e1),
                Arguments.of(3, e2),
                Arguments.of(6, e3),
                Arguments.of(9, Optional.empty())
        );
    }
}
