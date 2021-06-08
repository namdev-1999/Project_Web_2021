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

    @Column(nullable = false)
    private double total;

    @Column(length = 60, nullable = false)
    private String title;

    @Column(length = 255, nullable = false)
    private String contain;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", total=" + total +
                ", title='" + title + '\'' +
                ", contain='" + contain + '\'' +
                ", date=" + date +
                ", customer=" + customer +
                '}';
    }
}
