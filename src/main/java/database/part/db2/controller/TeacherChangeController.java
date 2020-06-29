package database.part.db2.controller;

import database.part.db2.dto.StudentWithGrade;
import database.part.db2.entity.Course;
import database.part.db2.entity.Grade;
import database.part.db2.entity.Student;
import database.part.db2.entity.Teacher;
import database.part.db2.mapper.GradeMapper;
import database.part.db2.mapper.StudentMapper;
import database.part.db2.service.ITeacherService;
import database.part.db2.service.impl.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class TeacherChangeController {
    @Autowired
    GradeMapper gradeMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    TeacherService service;
    //获取权限信息
    Authentication au = SecurityContextHolder.getContext().getAuthentication();

    Teacher t_info = service.getInfo(au);

    @RequestMapping("/teacherChangeGrade")
    String teacherChangeGrade(Model model,HttpServletRequest request, Long studentId, Long courseId){

        HttpSession session = request.getSession();
        //上传相关信息
        String username = t_info.getName();
        session.setAttribute("username", username);
        Course course = service.findCourse(courseId);
        session.setAttribute("course", course);
        //查找学生当前成绩
        Student student = studentMapper.findById(studentId);
        Grade grade = service.findGrade(studentId,courseId);
        //成绩和姓名
        session.setAttribute("grade", grade);
        session.setAttribute("student", student);
        //获取新的成绩
        int s = (int) session.getAttribute("newPoint");

        //更新
        grade.setPoint(s);
        gradeMapper.update(grade);

        return "teacherLookGrade";
    }
}
