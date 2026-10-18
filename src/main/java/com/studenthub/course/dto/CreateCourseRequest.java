package com.studenthub.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCourseRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String code;

    @NotNull
    private Long teacherId;
}
