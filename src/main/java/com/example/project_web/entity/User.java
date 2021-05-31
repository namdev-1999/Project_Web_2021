package com.example.project_web.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @Column
    private String phone;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String role;

    @Column
    private String avatar;

    @Column
    private String status;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date dob;

    @Column
    private String resetPasswordToken;

    public static Map<String, String> verifyCodes = new HashMap<>();
    public static Map<String, User> tempUsers = new HashMap<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", avatar='" + avatar + '\'' +
                ", status='" + status + '\'' +
                ", dob=" + dob +
                '}';
    }
}
