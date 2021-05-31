package com.example.project_web.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer_Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_course")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;
}
