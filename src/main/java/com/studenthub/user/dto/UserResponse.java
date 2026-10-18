package com.studenthub.user.dto;

import com.studenthub.user.Role;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponse {
    private Long id;
    private String name;
    private String surname;
    private Integer yearOfStudy;
    private String email;
    private Role role;
}
