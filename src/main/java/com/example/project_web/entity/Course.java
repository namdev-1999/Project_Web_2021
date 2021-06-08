package com.example.project_web.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date start_date;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date end_date;

    @Column(nullable = false)
    private Double price;

    @Column(length = 255)
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_trainer")
    private Trainer trainer;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Attendance> attendances = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Customer_Course> customer_courses = new HashSet<>();

}
