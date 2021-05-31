package com.example.project_web.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_course")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;
}
