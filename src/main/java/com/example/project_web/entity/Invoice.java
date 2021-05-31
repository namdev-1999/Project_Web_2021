package com.example.project_web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private double total;

    @Column
    private String title;

    @Column
    private String contain;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;
}
