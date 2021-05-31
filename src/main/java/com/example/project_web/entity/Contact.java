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
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String email;

    @Column
    private String content;

    @Column
    private String subject;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date start_date;


}
