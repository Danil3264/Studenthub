package com.studenthub.course;

import com.studenthub.course.dto.CourseResponse;
import com.studenthub.teacher.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public List<CourseResponse> getAllCourses(){
        List<Course> courses =  courseRepository.findAll();
        return courses.stream().map(this::courseResponseMapper).toList();
    }

    public CourseResponse getCourseById(Long id){
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found with id: " + id));
        return courseResponseMapper(course);
    }

    private CourseResponse courseResponseMapper(Course course){
        Teacher teacher = course.getTeacher();
        String teacherName = null;
        String teacherSurname = null;

        if(teacher != null){
            teacherName = teacher.getName();
            teacherSurname = teacher.getSurname();
        }

        return new CourseResponse(course.getId(), course.getTitle(), teacherName, teacherSurname, course.getCode(), course.getDescription());
    }



}
