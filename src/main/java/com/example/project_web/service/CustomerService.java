package com.example.project_web.service;

import com.example.project_web.entity.Customer;
import com.example.project_web.entity.Invoice;
import com.example.project_web.entity.User;
import com.example.project_web.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Lấy danh sách toàn bộ customer đã sắp xếp
    public List<Customer> findAll() {
        List<Customer> customerList = customerRepository.findAll();
        customerList.sort(new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                return o2.getRegister_date().compareTo(o1.getRegister_date());
            }
        });
        return customerList;
    }

    // Lấy danh sách toàn bộ customer
    public List<Customer> getAll() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList;
    }

    public int getCustomerByYearAndMonth(int year, int month) {
        int result = 0;
        List<Customer> list = customerRepository.findAll();
        for (Customer customer : list
        ) {
            if ((customer.getRegister_date().getYear() + 1900) == year && (customer.getRegister_date().getMonth() + 1) == month)
                result++;
        }
        return result;
    }

    // Tìm customer theo id
    public Customer findCustomerById(int id) {
        return customerRepository.findCustomerById(id);
    }

    //Thêm customer mới
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Chỉnh sửa thông tin customer
    public void update(Customer customer) {
        Customer c = customerRepository.findCustomerById(customer.getId());
        c.setName(customer.getName());
        c.setEmail(customer.getEmail());
        c.setPhone(customer.getPhone());
        c.setDob(customer.getDob());
        c.setRegister_date(customer.getRegister_date());
        c.setLevel(customer.getLevel());
        customerRepository.save(c);
    }

    // Xóa customer theo id
    public void deleteById(int id) {
        customerRepository.deleteById(id);
    }

}
