package com.hongikDB.api.controller;

import com.hongikDB.api.model.Student;
import com.hongikDB.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students") 
public class controller {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/degree")
    public ResponseEntity<String> getStudentDegree(@RequestParam String name) {
        List<Student> students = studentRepository.findByName(name);

        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such student");
        } 
        else if (students.size() > 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There are multiple students with the same name. Please provide an email address instead.");
        }
        else {
            Student std = students.get(0);
            return ResponseEntity.ok(std.getName()+" : "+ std.getDegree());
        }
    }

    @GetMapping("/email")
    public ResponseEntity<String> getStudentEmail(@RequestParam String name) {
        List<Student> students = studentRepository.findByName(name);

        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such student");
        } 
        else if (students.size() > 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There are multiple students with the same name. Please contact the administrator by phone.");
        } 
        else {
            Student std = students.get(0);
            return ResponseEntity.ok(std.getName()+" : "+ std.getEmail());
        }
    }

    @GetMapping("/stat")
    public ResponseEntity<String> getStudentStats(@RequestParam String degree) {
        long num = studentRepository.countByDegree(degree);
        return ResponseEntity.ok("Number of "+ degree +"'s" +" student : "+num);
    }

    @PutMapping("/register")
    public ResponseEntity<String> registerStudent(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam Integer graduation,
            @RequestParam String degree) {
        
        if (studentRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Already registered");
        }

        Student newStudent = new Student();
        newStudent.setName(name);
        newStudent.setEmail(email);
        newStudent.setGraduation(graduation);
        newStudent.setDegree(degree); 

        studentRepository.save(newStudent); 
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful");
    }
}