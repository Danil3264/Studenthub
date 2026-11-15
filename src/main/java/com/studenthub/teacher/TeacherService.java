package com.studenthub.teacher;

import com.studenthub.teacher.dto.CreateTeacherRequest;
import com.studenthub.teacher.dto.TeacherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public List<TeacherResponse> getAllTeachers(){
        return teacherRepository.findAll().stream().map(this::teacherResponseMapper).toList();
    }

    public TeacherResponse getTeacherById(Long id){
        Teacher teacher =  teacherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher is not found with id: " + id));
        return teacherResponseMapper(teacher);
    }

    public TeacherResponse createTeacher(CreateTeacherRequest request){

        if(teacherRepository.existsByEmail(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Teacher with this email is exists" + request.getEmail());
        }

        Teacher teacher = new Teacher();
        teacher.setName(request.getName());
        teacher.setSurname(request.getSurname());
        teacher.setEmail(request.getEmail());
        teacher.setTitle(request.getTitle());
        teacher.setDepartment(request.getDepartment());

        teacherRepository.save(teacher);

        return teacherResponseMapper(teacher);
    }

    public void deleteTeacher(Long id){

        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher with this id doesn't exist" + id));
        teacherRepository.delete(teacher);

    }

    private TeacherResponse teacherResponseMapper(Teacher teacher){
        return new TeacherResponse(teacher.getName(), teacher.getSurname(), teacher.getId(),  teacher.getTitle(), teacher.getEmail(), teacher.getDepartment());
    }

}
