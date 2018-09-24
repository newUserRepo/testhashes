package com.example.demo.layout.dbJdbc;


import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class CustomerService {

   private static final String SELECT = "SELECT id, first_name, last_name FROM customers";
    private static final String UPDATE = "UPDATE customers SET first_name=?, last_name=? WHERE id=?";
    private static final String INSERT = "INSERT into customers(id,first_name,last_name) VALUE(%d,'%s','%s')";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Customer> findAll() {
        return jdbcTemplate.query(
                SELECT,(rs,column) -> new Customer(rs.getLong("id"),
                        rs.getString("first_name"), rs.getNString("last_name"))
        );
    }

    public void update(Customer customer) {
        jdbcTemplate.update(UPDATE,customer.getFirstName(),customer.getLastName(),customer.getId());
    }

    public Customer insert(final Customer customer) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {

            final PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());

            return ps;
        },keyHolder);

        final long customerId = keyHolder.getKey().longValue();
        customer.setId(customerId);
        return customer;
    }
}
