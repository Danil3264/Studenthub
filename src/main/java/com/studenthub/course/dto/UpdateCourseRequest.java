package com.studenthub.course.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCourseRequest {
    private String title;

    private String description;

    private Long teacherId;
}
