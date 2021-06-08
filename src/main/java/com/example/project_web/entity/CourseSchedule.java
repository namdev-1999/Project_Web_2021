package com.example.project_web.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "course_schedule")
public class CourseSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 30, nullable = false)
    private String course;

    @Column(length = 30, nullable = false)
    private String trainer;

    @Column(length = 15, nullable = false)
    private String day;

    @Column(columnDefinition = "TIME NOT NULL", nullable = false)
    private String time_from;

    @Column(columnDefinition = "TIME NOT NULL", nullable = false)
    private String time_to;

    @Column(length = 20)
    private String room;

    @Column(length = 255)
    private String description;
}
