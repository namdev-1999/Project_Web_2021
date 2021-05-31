package com.example.project_web.controller;

import com.example.project_web.entity.*;
import com.example.project_web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RestFullAPI {

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private TrainerService trainerService;

    //======================================================== FOR USER ========================================================//
    // Lấy toàn bộ danh sách user
    @GetMapping(value = "/users/", produces = "application/json")
    public ResponseEntity<?> getAllUser() {
        List<User> userList = userDetailService.findAll();
        if (userList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    // Lấy user theo id
    @GetMapping(value = "/users/{id}", produces = "application/json")
    public ResponseEntity<?> getUserById(@PathVariable("id") int id) {
        if (userDetailService.findUserById(id) == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userDetailService.findUserById(id), HttpStatus.OK);
        }
    }

    // Thêm user mới
    @PostMapping(value = "/users/add")
    public String createNewUser(@ModelAttribute User user, HttpServletResponse response) {
        userDetailService.addUser(user);
        try {
            response.sendRedirect("/admin_userlist");
        } catch (IOException e) {
            return "Cannot redirect";
        }
        return "OK";
    }

    // Chỉnh sửa thông tin user
    @PutMapping(value = "/users/edit/{id}", produces = "application/json")
    public ResponseEntity<?> editUserById(@PathVariable("id") int id, @RequestBody User user) {
        if (userDetailService.findUserById(id) == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } else {
            userDetailService.update(user);
            return new ResponseEntity<>("Updated successfully!", HttpStatus.OK);
        }
    }

    // Xóa user
    @DeleteMapping(value = "/users/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") int id) {
        if (userDetailService.findUserById(id) == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        System.out.println("Not NULL");
        userDetailService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully!");
    }

    // Lấy thông tin user đăng nhập
    @GetMapping(value = "/users/auth", produces = "application/json")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(userDetailService.findUserByEmail(authentication.getName()), HttpStatus.OK);
    }

    //======================================================== FOR COURSE ========================================================//
    // Lấy danh sách các khóa học
    @GetMapping(value = "/courses", produces = "application/json")
    public ResponseEntity<?> getAllCourse() {
        List<Course> courseList = coursesService.findAll();
        if (courseList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courseList, HttpStatus.OK);
    }

    // Thêm course mới
    @PostMapping(value = "/courses/add")
    public String createNewCourse(@ModelAttribute Course course, HttpServletResponse response) {
        coursesService.addCourse(course);
        try {
            response.sendRedirect("/courses");
        } catch (IOException e) {
            return "Cannot redirect";
        }
        return "OK";
    }

    // Chỉnh sửa thông tin course
    @PutMapping(value = "/courses/edit/{id}", produces = "application/json")
    public ResponseEntity<?> editCourseById(@PathVariable("id") int id, @RequestBody Course course) {
        if (coursesService.findCourseById(id) == null) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        } else {
            coursesService.update(course);
            return new ResponseEntity<>("Updated successfully!", HttpStatus.OK);
        }
    }

    // Xóa course
    @DeleteMapping(value = "/courses/delete/{id}")
    public ResponseEntity<?> deleteCourseById(@PathVariable("id") int id) {
        if (coursesService.findCourseById(id) == null) {
            return ResponseEntity.badRequest().body("Course not found");
        }
        System.out.println("Not NULL");
        coursesService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully!");
    }

    //======================================================== FOR CUSTOMER ========================================================//
    // Lấy toàn bộ danh sách customer
    @GetMapping(value = "/customers", produces = "application/json")
    public ResponseEntity<?> getAllCustomer() {
        List<Customer> customerList = customerService.getAll();
        if (customerList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    // Chỉnh sửa thông tin customer
    @PutMapping(value = "/customers/edit/{id}", produces = "application/json")
    public ResponseEntity<?> editCustomerById(@PathVariable("id") int id, @RequestBody Customer customer) {
        if (customerService.findCustomerById(id) == null) {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        } else {
            customerService.update(customer);
            return new ResponseEntity<>("Updated successfully!", HttpStatus.OK);
        }
    }

    // Xóa customer
    @DeleteMapping(value = "/customers/delete/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable("id") int id) {
        if (customerService.findCustomerById(id) == null) {
            return ResponseEntity.badRequest().body("Trainer not found");
        }
        customerService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully!");
    }

    // Thêm customer mới
    @PostMapping(value = "/customers/add")
    public String createNewCustomer(@ModelAttribute Customer customer, HttpServletResponse response) {
        customerService.addCustomer(customer);
        try {
            response.sendRedirect("/admin_customerslist");
        } catch (IOException e) {
            return "Cannot redirect";
        }
        return "OK";
    }

    //======================================================== FOR TRAINER ========================================================//
    // Lấy toàn bộ danh sách trainer
    @GetMapping(value = "/trainers/", produces = "application/json")
    public ResponseEntity<?> getAllTrainer() {
        List<Trainer> trainerList = trainerService.findAll();
        if (trainerList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(trainerList, HttpStatus.OK);
    }

    // Chỉnh sửa thông tin trainer
    @PutMapping(value = "/trainers/edit/{id}", produces = "application/json")
    public ResponseEntity<?> editTrainerById(@PathVariable("id") int id, @RequestBody Trainer trainer) {
        if (trainerService.findTrainerById(id) == null) {
            return new ResponseEntity<>("Trainer not found", HttpStatus.NOT_FOUND);
        } else {
            trainerService.update(trainer);
            return new ResponseEntity<>("Updated successfully!", HttpStatus.OK);
        }
    }

    // Xóa trainer
    @DeleteMapping(value = "/trainers/delete/{id}")
    public ResponseEntity<?> deleteTrainerById(@PathVariable("id") int id) {
        if (trainerService.findTrainerById(id) == null) {
            return ResponseEntity.badRequest().body("Trainer not found");
        }
        trainerService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully!");
    }

    // Thêm trainer mới
    @PostMapping(value = "/trainers/add")
    public String createNewTrainer(@ModelAttribute Trainer trainer, HttpServletResponse response) {
        trainerService.addTrainer(trainer);
        try {
            response.sendRedirect("/admin_trainers");
        } catch (IOException e) {
            return "Cannot redirect";
        }
        return "OK";
    }

    //======================================================== FOR INVOICE ========================================================//
    // Lấy danh sách invoices theo tháng và năm
    @GetMapping(value = "/invoicesrevenue/{year}", produces = "application/json")
    public ResponseEntity<?> getAllInvoice(@PathVariable("year") int year) {
        Map<Integer, Double> invoiceListGroupYear = invoiceService.invoiceListGroupYear(year);
        return new ResponseEntity<>(invoiceListGroupYear, HttpStatus.OK);
    }

    // Lấy toàn bộ danh sách invoices
    @GetMapping(value = "/invoices/", produces = "application/json")
    public ResponseEntity<?> getAllInvoice() {
        List<Invoice> invoiceList = invoiceService.findAll();
        if (invoiceList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(invoiceList, HttpStatus.OK);
    }

    // Lấy invoice theo id
    @GetMapping(value = "/invoices/{id}")
    public Invoice getOneInvoice(@PathVariable("id") int id) {
        Invoice invoiceList = invoiceService.getOne(id);
        if (invoiceList == null) {
            ResponseEntity.notFound().build();
        }
        return invoiceList;
    }

    // Chỉnh sửa thông tin invoice
    @PutMapping(value = "/invoices/edit/{id}", produces = "application/json")
    public ResponseEntity<?> editInvoiceById(@PathVariable("id") int id, @RequestBody Invoice invoice) {
        if (invoiceService.findInvoiceById(id) == null) {
            return new ResponseEntity<>("Invoice not found", HttpStatus.NOT_FOUND);
        } else {
            invoiceService.update(invoice);
            return new ResponseEntity<>("Updated successfully!", HttpStatus.OK);
        }
    }

    // Xóa invoice
    @DeleteMapping(value = "/invoices/delete/{id}")
    public ResponseEntity<?> deleteInvoiceById(@PathVariable("id") int id) {
        if (invoiceService.findInvoiceById(id) == null) {
            return ResponseEntity.badRequest().body("Invoice not found");
        }
        invoiceService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully!");
    }

    // Thêm invoice mới
    @PostMapping(value = "/invoices/add")
    public String createNewInvoice(@ModelAttribute Invoice invoice, HttpServletResponse response) {
        invoiceService.addInvoice(invoice);
        try {
            response.sendRedirect("/receipt");
        } catch (IOException e) {
            return "Cannot redirect";
        }
        return "OK";
    }

    // lấy số lượng customer
    @GetMapping(value = "/count_customer_{year}", produces = "application/json")
    public ResponseEntity<?> getCountCustomer(@PathVariable("year") int year) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            result.add(customerService.getCustomerByYearAndMonth(year, i + 1));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
