package com.example.project_web.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String description;

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL)
    private Set<Course> courses;
}
