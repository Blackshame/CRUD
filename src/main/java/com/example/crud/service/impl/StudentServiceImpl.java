package com.example.crud.service.impl;

import com.example.crud.entity.Student;
import com.example.crud.mapper.StudentMapper;
import com.example.crud.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    //添加新的学生信息
    @Override
    public void  addStudent(String studentid, String name, String hometown)
    {
        studentMapper.addStudent(studentid, name, hometown);
    }

    //更新学生信息
    @Override
    public void updateStudent(String studentid, String name, String hometown) {
        studentMapper.updateStudent(studentid, name, hometown);
    }

    //删除学生信息
    @Override
    public void deleteStudent(String studentid) {
        studentMapper.deleteStudent(studentid);
    }

    //根据学生学号查询学生信息
    @Override
    public Student getStudent(String studentid) {
        return studentMapper.getStudent(studentid);
    }

    //获取所有学生信息
    @Override
    public List<Student> getAllStudents() {
        return studentMapper.getAllStudents();
    }


}
