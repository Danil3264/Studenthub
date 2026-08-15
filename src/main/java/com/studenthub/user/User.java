package com.studenthub.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private int yearOfStudy;
    @Column(unique = true, nullable = false)
    private String email;

    private String passwordHash;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime createdAt;
}
