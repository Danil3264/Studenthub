package com.studenthub.course.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private Long id;

    private String title;

    private String teacherName;

    private String teacherSurname;

    private String code;

    private String description;
}
