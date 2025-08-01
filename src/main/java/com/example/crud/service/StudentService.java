package com.example.crud.service;

import com.example.crud.entity.Student;

import java.util.List;

public interface StudentService {
    void  addStudent(String studentid, String name, String hometown);
    void  updateStudent(String studentid, String name, String hometown);
    void  deleteStudent(String studentid);
    Student getStudent(String studentid);
    List<Student> getAllStudents();
}
