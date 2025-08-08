package com.example.crud.service.impl;

import com.example.crud.entity.Student;
import com.example.crud.mapper.StudentMapper;
import com.example.crud.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final String CACHE_PREFIX = "student:";
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 工具

    //添加新的学生信息，同时写入缓存
    @Override
    public void  addStudent(String studentid, String name, String hometown)
    {
        studentMapper.addStudent(studentid, name, hometown);

        Student student = new Student();
        student.setStudentid(studentid);
        student.setName(name);
        student.setHometown(hometown);

        cacheStudent(student);
    }

    //更新学生信息，同时刷新缓存
    @Override
    public void updateStudent(String studentid, String name, String hometown) {
        studentMapper.updateStudent(studentid, name, hometown);

        Student updated = new Student();
        updated.setStudentid(studentid);
        updated.setName(name);
        updated.setHometown(hometown);

        cacheStudent(updated);
    }

    //删除学生信息，同时删除缓存
    @Override
    public void deleteStudent(String studentid) {
        studentMapper.deleteStudent(studentid);
        stringRedisTemplate.delete(CACHE_PREFIX + studentid);
    }

    //根据学生学号查询学生信息，先查缓存再查数据库
    @Override
    public Student getStudent(String studentid) {
        String key = CACHE_PREFIX + studentid;

        // 1. 查缓存
        String json = stringRedisTemplate.opsForValue().get(key);
        if (json != null) {
            try {
                return objectMapper.readValue(json, Student.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace(); // 可用日志替换
            }
        }

        // 2. 没查到缓存，查数据库
        Student student = studentMapper.getStudent(studentid);
        if (student != null) {
            cacheStudent(student); // 回写缓存
        }

        return student;
    }

    //获取所有学生信息
    @Override
    public List<Student> getAllStudents() {
        return studentMapper.getAllStudents();
    }

    // 缓存封装
    private void cacheStudent(Student student) {
        try {
            // 1. 生成 Redis 缓存的 key，格式是 "student:" + 学号，比如 "student:2024001"
            String key = CACHE_PREFIX + student.getStudentid();

            // 2. 将 Student 对象序列化成 JSON 字符串，方便存储到 Redis 中
            String json = objectMapper.writeValueAsString(student);

            // 3. 使用 StringRedisTemplate 将 JSON 字符串存入 Redis，设置过期时间为10分钟
            stringRedisTemplate.opsForValue().set(key, json, Duration.ofMinutes(10)); // 设置 10 分钟过期
        } catch (JsonProcessingException e) {
            // 4. 如果序列化过程中出现异常，则打印错误信息
            e.printStackTrace();
        }
    }


}
