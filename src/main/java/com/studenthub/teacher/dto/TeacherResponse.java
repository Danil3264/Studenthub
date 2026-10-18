package com.studenthub.teacher.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherResponse {
    private String name;
    private String surname;
    private Long id;
    private String title;
    private String email;
    private String department;
}
