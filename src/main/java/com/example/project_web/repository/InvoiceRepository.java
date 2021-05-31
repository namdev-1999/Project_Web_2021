package com.example.project_web.repository;

import com.example.project_web.entity.Invoice;
import com.example.project_web.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    @Query(value = "SELECT year(date), month(date), sum(total) FROM invoice group by year(date), month(date)", nativeQuery = true)
    List<String> invoiceListGroupYear();

    @Query(value = "SELECT SUM(total) totalIncome, count(total) orderCount FROM invoice GROUP BY date HAVING date = CURDATE()", nativeQuery = true)
    List<String> invoiceListGroupToday();

    Invoice findInvoiceById(int id);

    void deleteById(int id);
}
