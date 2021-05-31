package com.example.project_web.repository;

import com.example.project_web.entity.Customer;
import com.example.project_web.entity.Invoice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findCustomerById(int id);

    void deleteById(int id);
}
