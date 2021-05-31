package com.example.project_web.service;

import com.example.project_web.entity.Invoice;
import com.example.project_web.entity.User;
import com.example.project_web.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    // Lấy toàn bộ danh sách invoice
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    // Tìm invoice theo id
    public Invoice findInvoiceById(int id) {
        return invoiceRepository.findInvoiceById(id);
    }


    public Map<Integer, Double> invoiceListGroupYear(int year) {
        List<String> invoiceListGroupYear = invoiceRepository.invoiceListGroupYear();
        Map<Integer, Double> result = new HashMap<>();//month, income
        String[] temp;
        for (String invoice:invoiceListGroupYear             ) {
            temp = invoice.split(","); // year, month, imcome
            if(Integer.parseInt(temp[0]) == year)
                result.put(Integer.parseInt(temp[1]), Double.parseDouble(temp[2]));
        }
        return result;
    }

    public List<String> invoiceListGroupToday() {
        return  invoiceRepository.invoiceListGroupToday();
    }

    public Invoice getOne(int id) {
        return invoiceRepository.getOne(id);
    }

    //Thêm invoice mới
    public Invoice addInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    // Chỉnh sửa thông tin invoice
    public void update(Invoice invoice) {
        Invoice i = invoiceRepository.findInvoiceById(invoice.getId());
        i.setTitle(invoice.getTitle());
        i.setContain(invoice.getContain());
        i.setTotal(invoice.getTotal());
        i.setDate(invoice.getDate());
        invoiceRepository.save(i);
    }

    // Xóa invoice theo id
    public void deleteById(int id) {
        invoiceRepository.deleteById(id);
    }
}
