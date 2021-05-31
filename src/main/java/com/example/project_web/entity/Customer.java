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

    @Column
    private String name;

    @Column
    private String phone;

    @Column
    private String email;

     @Column
    private String level;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date dob;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date register_date;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Invoice> invoices = new HashSet<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Attendance> attendances;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Customer_Course> customer_courses;

}
