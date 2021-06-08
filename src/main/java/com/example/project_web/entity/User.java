package com.example.project_web.entity;

import lombok.*;

import javax.persistence.*;
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

    @Column(length = 60, nullable = false)
    private String name;

    @Column
    private String phone;

    @Column(unique = true, length = 65, nullable = false)
    private String email;

    @Column(length = 128, nullable = false)
    private String password;

    @Column(length = 10)
    private String role;

    @Column
    private String avatar;

    @Column(length = 10)
    private String status;

    @Column
    @Temporal(value = TemporalType.DATE)
    private Date dob;

    @Column(length = 30)
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
