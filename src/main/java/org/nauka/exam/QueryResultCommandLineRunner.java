package org.nauka.exam;

import org.nauka.exam.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class QueryResultCommandLineRunner implements CommandLineRunner {
    private final EmployeeService service;

    @Autowired
    public QueryResultCommandLineRunner(EmployeeService service) {
        this.service = service;
    }

    private void printFindByIdResult(String id) {
        System.out.println("findById(" + id + ") result:");
        System.out.println(service.findById(Integer.parseInt(id)));
        System.out.println();
    }

    private void printGroupByNameResult() {
        System.out.println("groupById() result:");
        var map = service.groupByName();
        for (var key : map.keySet()) {
            System.out.println(key + " : " + map.get(key));
        }
        System.out.println();
    }

    private void printFindBetweenResult(String from, String to) {
        System.out.println("findBetween(" + from + ", " + to + ") result:");
        System.out.println(service.findBetween(
                LocalDate.parse(from),
                LocalDate.parse(to))
        );
        System.out.println();
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length >= 3) {
            printFindByIdResult(args[0]);
            printGroupByNameResult();
            printFindBetweenResult(args[1], args[2]);
        }
        else {
            System.out.println("There are not enough arguments to print query results");
        }
    }
}
