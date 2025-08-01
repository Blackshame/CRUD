package com.example.crud.mapper;

import com.example.crud.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    //添加新的学生信息
    @Insert("insert into students(student_id,name,hometown)"+"values(#{studentid},#{name},#{hometown})")
    void addStudent(String studentid,String name,String hometown);

    //更新学生信息
    @Update("update students set name=#{name},hometown=#{hometown}"+"where student_id=#{studentid}")
    void updateStudent(String studentid,String name,String hometown);

    //删除学生信息
    @Delete("delete from students where student_id=#{studentid}")
    void deleteStudent(String studentid);

    //根据学生学号查询学生信息
    @Select("select * from students where student_id=#{studentid}")
    Student getStudent(String studentid);

    //查询所有学生信息
    @Select("select * from students")
    List<Student> getAllStudents();
}
