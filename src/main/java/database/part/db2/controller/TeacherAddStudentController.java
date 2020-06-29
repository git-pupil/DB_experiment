package database.part.db2.controller;

import database.part.db2.entity.Course;
import database.part.db2.entity.Grade;
import database.part.db2.entity.Teacher;
import database.part.db2.mapper.GradeMapper;
import database.part.db2.service.ITeacherService;
import database.part.db2.service.impl.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class TeacherAddStudentController {
    @Autowired
    GradeMapper gradeMapper;

    @Autowired
    TeacherService service;
    //获取权限
    Authentication au = SecurityContextHolder.getContext().getAuthentication();
    Teacher t_info = service.getInfo(au);

    @RequestMapping("/teacherChangeGrade")
    String teacherChangeGrade(Model model,HttpServletRequest request, Long studentId, Long courseId){
        HttpSession session = request.getSession();
        //上传相关信息
        String username = t_info.getName();
        session.setAttribute("username", username);
        Course course = service.findCourse(courseId);
        //获取学生的id
        model.getAttribute("id");
        //当前grade表条目数 以获取新的id
        Long num = Long.valueOf(service.count());
        //设置条目信息
        Grade grade = new Grade();
        grade.setCourseId(courseId);
        grade.setStudentId(Long.valueOf((String) session.getAttribute("id")));
        grade.setId(++num);
        grade.setPoint(0);
        //新增
        gradeMapper.create(grade);

        return "teacherLookGrade";
    }
}
