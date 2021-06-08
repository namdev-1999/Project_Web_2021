package com.example.project_web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 60, nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String phone;

    @Column(length = 60, nullable = false)
    private String email;

    @Column(length = 10, nullable = false)
    private String level;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date dob;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date register_date;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Invoice> invoices = new HashSet<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Attendance> attendances = new HashSet<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Customer_Course> customer_courses = new HashSet<>();


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", level='" + level + '\'' +
                ", dob=" + dob +
                ", register_date=" + register_date +
                ", invoices=" + invoices +
                ", attendances=" + attendances +
                ", customer_courses=" + customer_courses +
                '}';
    }
}
