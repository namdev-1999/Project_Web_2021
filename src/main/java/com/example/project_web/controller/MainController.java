package com.example.project_web.controller;

import com.example.project_web.entity.Course;
import com.example.project_web.entity.CourseSchedule;
import com.example.project_web.entity.Trainer;
import com.example.project_web.service.CourseScheduleService;
import com.example.project_web.service.CoursesService;
import com.example.project_web.service.InvoiceService;
import com.example.project_web.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private CoursesService coursesService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private CourseScheduleService courseScheduleService;

    //================== FOR USER ==================//
    @RequestMapping(value = "/")
    public String welcome() {
        return "index-user";
    }


    //================== FOR ADMIN ==================//
    @GetMapping(value = "/index")
    public String index(Model model) {
        int orderCount = 0;
        int totalIncome = 0;
        List<String> invoiceListGroupToday = invoiceService.invoiceListGroupToday();
        if (!invoiceListGroupToday.isEmpty()) {
            String[] temp = invoiceListGroupToday.get(0).split(",");
            orderCount = Integer.parseInt(temp[1]);
            totalIncome = (int) Double.parseDouble(temp[0]) / 1000;
        }
        model.addAttribute("orderCount", orderCount);
        model.addAttribute("totalIncome", totalIncome);
        return "index";
    }

    @RequestMapping(value = "/receipt")
    public String receipt() {
        return "receipt";
    }

    @RequestMapping(value = "/register")
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/verify")
    public String verify(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "verify";
    }

    @RequestMapping(value = "/add_receipt")
    public String addReceipt() {
        return "add_receipt";
    }

    @RequestMapping(value = "/add_customers")
    public String add_customers() {
        return "add_customers";
    }

    @RequestMapping(value = "/add_faq")
    public String add_faq() {
        return "add_faq";
    }

    @RequestMapping(value = "/add_gallery")
    public String add_gallery() {
        return "add_gallery";
    }

    @RequestMapping(value = "/admin_addnews")
    public String admin_addnews() {
        return "admin_addnews";
    }

    @RequestMapping(value = "/add_course")
    public String add_course() {
        return "add_course";
    }

    @RequestMapping(value = "/add_trainer")
    public String add_trainer() {
        return "add_trainer";
    }

    @GetMapping(value = "/admin_courseschedule")
    public String admin_courseschedule(Model model) {
        List<String> courseList = coursesService.findAllByName();
        List<String> trainerList = trainerService.findAllByName();

        model.addAttribute("courseList", courseList);
        model.addAttribute("trainerList", trainerList);
        return "admin_courseschedule";
    }

    @RequestMapping(value = "/admin_customerlist")
    public String admin_customerslist() {
        return "admin_customerlist";
    }

    @RequestMapping(value = "/admin_customerpayment")
    public String admin_customerpayment() {
        return "admin_customerpayment";
    }

    @RequestMapping(value = "/admin_customerprofile")
    public String admin_customerprofile() {
        return "admin_customerprofile";
    }

    @RequestMapping(value = "/admin_rooms")
    public String admin_rooms() {
        return "admin_rooms";
    }

    @RequestMapping(value = "/admin_trainers")
    public String admin_trainers() {
        return "admin_trainers";
    }

    @RequestMapping(value = "/admin_userpayment")
    public String admin_userpayment() {
        return "admin_userpayment";
    }


    @GetMapping(value = "/admin_userlist")
    public String admin_userlist() {
        return "admin_userlist";
    }

    @RequestMapping(value = "/admin_timings")
    public String admin_timings() {
        return "admin_timings";
    }

    @RequestMapping(value = "/admin_coupon")
    public String admin_coupon() {
        return "admin_coupon";
    }

    @RequestMapping(value = "/courses")
    public String courses() {
        return "courses";
    }

    @RequestMapping(value = "/index-2")
    public String dashboard() {
        return "index-2";
    }

    @RequestMapping(value = "/event_item")
    public String event_item() {
        return "event_item";
    }

    @RequestMapping(value = "/events_list")
    public String events_list() {
        return "events_list";
    }

    @RequestMapping(value = "/faq")
    public String faq() {
        return "faq";
    }

    @RequestMapping(value = "/gallery")
    public String gallery() {
        return "gallery";
    }

    @RequestMapping(value = "/profile_view")
    public String profile_view() {
        return "profile_view";
    }


}
