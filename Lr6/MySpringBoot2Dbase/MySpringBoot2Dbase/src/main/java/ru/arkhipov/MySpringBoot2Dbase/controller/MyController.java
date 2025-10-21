package ru.arkhipov.MySpringBoot2Dbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.arkhipov.MySpringBoot2Dbase.entity.Student;
import ru.arkhipov.MySpringBoot2Dbase.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MyController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public ResponseEntity<Object> allStudents(){
        List<Student> allStudents = studentService.getAllStudents();
        if(allStudents.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The students list is empty");
        }
        return ResponseEntity.ok(allStudents);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Object> getStudent(@PathVariable("id") int id){
        Student student = studentService.getStudent(id);
        if(student == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student with ID " + id + " not found");
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping("/students")
    public ResponseEntity<Object> saveStudent(@RequestBody Student student){
        try {
            Student saveStudent = studentService.saveStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Student " + saveStudent + " successfully added");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during adding student: " + e.getMessage());
        }

    }

    @PutMapping("/students")
    public ResponseEntity<Object> updateStudent(@RequestBody Student student){
        if((student.getId() == 0) || studentService.getStudent(student.getId()) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cannot be updated: student with ID " + student.getId() + " not found");
        }

        try{
            studentService.saveStudent(student);
            return ResponseEntity.ok("Student info successfully updated: " + student);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during updating student info: " + e.getMessage());
        }
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable("id") int id){
        Student student = studentService.getStudent(id);
        if(student == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student with ID " + id + " not found");
        }
        try{
            studentService.deleteStudent(id);
            return ResponseEntity.ok("Student with ID " + id + " successfully expelled");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during expelling student: " + e.getMessage());
        }
    }
}
