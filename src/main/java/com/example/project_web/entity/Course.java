package com.example.project_web.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
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

    @Column
    private String name;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date start_date;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date end_date;

    @Column
    private Double price;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_trainer")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "day")
    private Day day;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Attendance> attendances;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Customer_Course> customer_courses;

}
