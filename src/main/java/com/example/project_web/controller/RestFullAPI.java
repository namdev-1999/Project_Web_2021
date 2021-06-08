package com.example.project_web.controller;

import com.example.project_web.entity.*;
import com.example.project_web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
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

    @Autowired
    private CourseScheduleService courseScheduleService;

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

    // Kiểm tra email đã tồn tại hay chưa ?
    @GetMapping(value = "/checkEmail", produces = "application/json")
    public String checkEmail(@RequestParam("email") String email) {
        User user = userDetailService.findUserByEmail(email);
        return String.format("{ \"valid\": %b }", user == null); //user null -> chưa có -> email hợp lệ -> không lỗi
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

    // Lấy customer theo id
    @GetMapping(value = "customers/{id}", produces = "application/json")
    public Customer getCustomer(@PathVariable("id") int id) {
        return customerService.findCustomerById(id);
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

    // lấy số lượng customer
    @GetMapping(value = "/count_customer_{year}", produces = "application/json")
    public ResponseEntity<?> getCountCustomer(@PathVariable("year") int year) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            result.add(customerService.getCustomerByYearAndMonth(year, i + 1));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
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

    // Lấy trainer theo id
    @GetMapping(value = "trainers/{id}", produces = "application/json")
    public Trainer trainer(@PathVariable("id") int id) {
        return trainerService.findTrainerById(id);
    }

    // Kiểm tra id có tồn tại hay không ?
    @GetMapping(value = "/id_trainer", produces = "application/json")
    public String checkIdTrainer(@RequestParam("id_trainer") int id) {
        Trainer trainer = trainerService.findTrainerById(id);
        return String.format("{ \"valid\": %b }", trainer != null); //customer null -> chưa có -> lỗi
    }

    //======================================================== FOR INVOICE ========================================================//
    // Lấy danh sách invoices theo tháng và năm
    @GetMapping(value = "/invoicesrevenue/{year}", produces = "application/json")
    public ResponseEntity<?> getAllInvoice(@PathVariable("year") int year) {
        Map<Integer, Double> invoiceListGroupYear = invoiceService.invoiceListGroupYear(year);
        return new ResponseEntity<>(invoiceListGroupYear, HttpStatus.OK);
    }

    // Kiểm tra id có tồn tại hay không ?
    @GetMapping(value = "/id_customer", produces = "application/json")
    public String checkIdCustomer(@RequestParam("id_customer") int id) {
        Customer customer = customerService.findCustomerById(id);
        return String.format("{ \"valid\": %b }", customer != null); //customer null -> chưa có -> lỗi
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


    //======================================================== FOR COURSE SCHEDULE ========================================================//
    // Lấy toàn bộ danh sách course schedule theo thứ
    @GetMapping(value = "/course_schedule/th", produces = "application/json")
    public ResponseEntity<?> getCourseScheduleByTh(@RequestParam("th") String th) {
        List<CourseSchedule> course_scheduleList = courseScheduleService.findCourseScheduleByTH(th);
        if (course_scheduleList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(course_scheduleList, HttpStatus.OK);
    }

    // Lấy toàn bộ danh sách course schedule
    @GetMapping(value = "/course_schedule", produces = "application/json")
    public ResponseEntity<?> getCourseSchedule() {
        List<CourseSchedule> course_scheduleList = courseScheduleService.findCourseSchedule();
        if (course_scheduleList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(course_scheduleList, HttpStatus.OK);
    }

    // Thêm course schedule mới
    @PostMapping(value = "/course_schedule/add")
    public String createNewCourseSchedule(@ModelAttribute CourseSchedule course_schedule, HttpServletResponse response) {
        courseScheduleService.addCourseSchedule(course_schedule);
        try {
            response.sendRedirect("/admin_courseschedule");
        } catch (IOException e) {
            return "Cannot redirect";
        }
        return "OK";
    }

    // Chỉnh sửa thông tin course schedule
    @PutMapping(value = "/course_schedule/edit/{id}", produces = "application/json")
    public ResponseEntity<?> editCourseScheduleById(@PathVariable("id") int id, @RequestBody CourseSchedule course_schedule) {
        if (courseScheduleService.findCourseScheduleById(id) == null) {
            return new ResponseEntity<>("Course Schedule not found", HttpStatus.NOT_FOUND);
        } else {
            courseScheduleService.update(course_schedule);
            return new ResponseEntity<>("Updated successfully!", HttpStatus.OK);
        }
    }

    // Xóa course schedule
    @DeleteMapping(value = "/course_schedule/delete/{id}")
    public ResponseEntity<?> deleteCourseScheduleById(@PathVariable("id") int id) {
        if (courseScheduleService.findCourseScheduleById(id) == null) {
            return ResponseEntity.badRequest().body("Course Schedule not found");
        }
        courseScheduleService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully!");
    }

    // Lấy course schedule theo id
    @GetMapping(value = "course_schedule/{id}", produces = "application/json")
    public CourseSchedule getCourseScheduleByTh(@PathVariable("id") int id) {
        return courseScheduleService.findCourseScheduleById(id);
    }


}
