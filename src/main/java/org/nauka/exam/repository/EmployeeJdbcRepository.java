package org.nauka.exam.repository;

import org.nauka.exam.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EmployeeJdbcRepository implements EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Employee> findById(long id) {
        String query = "SELECT * FROM employee WHERE id = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Employee.class), id).stream().findAny();
    }

    @Override
    public Map<String, Integer> groupByName() {
        String query = "SELECT name, COUNT(*) FROM employee GROUP BY name";
        return jdbcTemplate.query(query, new RowMapper<Pair<String, Integer>>() {
                    @Override
                    public Pair<String, Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return Pair.of(rs.getString("name"), rs.getInt("COUNT(*)"));
                    }
                })
                .stream()
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
    }

    @Override
    public List<Employee> findBetween(LocalDate from, LocalDate to) {
        String query = "SELECT * FROM employee WHERE birthday BETWEEN ? AND ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Employee.class), from, to).stream().toList();
    }
}
