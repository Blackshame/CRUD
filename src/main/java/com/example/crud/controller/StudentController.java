package com.example.crud.controller;
import com.example.crud.entity.Result;
import com.example.crud.entity.Student;
import com.example.crud.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    //添加新的学生信息
    @PostMapping
    public Result addStudent(@RequestBody Student student) {
        System.out.println("接收的 JSON 数据：" + student);

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
        System.out.println("路径中的 studentid = " + studentid);
        System.out.println("请求体中的 student = " + student);
        // 强制将路径中的 id 设置到 student 对象，确保一致
        student.setStudentid(studentid);
        if (studentService.getStudent(studentid) == null) {
            return Result.error("找不到该学生");
        }

        studentService.updateStudent(studentid, student.getName(), student.getHometown());
        return Result.success("学生更新成功，学号为 " + studentid);
    }


    //删除学生信息
    @DeleteMapping("/{studentid}")
    public Result deleteStudent(@PathVariable String studentid) {
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
        List<Student> students = studentService.getAllStudents();
        return Result.success("查询成功",students);
    }
}
