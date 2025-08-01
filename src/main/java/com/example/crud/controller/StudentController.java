package com.example.crud.controller;
import com.example.crud.entity.Result;
import com.example.crud.entity.Student;
import com.example.crud.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j

@RestController
@RequestMapping(value = "/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    //添加新的学生信息
    @PostMapping
    public Result addStudent(@RequestBody Student student) {
        log.info("【添加学生】请求体 student: {}", student);
        String studentid = student.getStudentid();
        if (studentService.getStudent(studentid) != null) {
            return Result.error("学号已存在");
        }

        studentService.addStudent(student.getStudentid(), student.getName(), student.getHometown());
        return Result.success("学生创建成功，新的学生学号是 " + studentid);
    }

    //更新学生信息
    @PutMapping("/{studentid}")
    public Result updateStudent(@PathVariable String studentid, @RequestBody Student student) {
        log.info("【更新学生】路径 studentid: {}", studentid);
        log.info("【更新学生】请求体 student: {}", student);
        // 强制将路径中的 id 设置到 student 对象，确保一致
        student.setStudentid(studentid);
        log.info("【更新前 student 设置后】：{}", student);
        if (studentService.getStudent(studentid) == null) {
            return Result.error("找不到该学生");
        }

        studentService.updateStudent(studentid, student.getName(), student.getHometown());
        return Result.success("学生更新成功，学号为 " + studentid);
    }


    //删除学生信息
    @DeleteMapping("/{studentid}")
    public Result deleteStudent(@PathVariable String studentid) {
        log.info("【删除学生】studentid: {}", studentid);
        Student student = studentService.getStudent(studentid);
        if(student == null){
            return Result.error("学号不存在");
        }
        else {
            studentService.deleteStudent(studentid);
            return Result.success("删除成功");
        }
    }

    //根据学号查询学生信息
    @GetMapping("/{studentid}")
    public Result getStudent(@PathVariable String studentid) {
        log.info("【查询学生】studentid: {}", studentid);
        Student student = studentService.getStudent(studentid);
        if(student == null){
            return Result.error("对应学生不存在");
        }
        else {
            return Result.success("查询成功",student);
        }
    }

    //查询所有学生的信息
    @GetMapping
    public Result getAllStudent() {
        log.info("【查询全部学生】");
        List<Student> students = studentService.getAllStudents();
        return Result.success("查询成功",students);
    }
}
