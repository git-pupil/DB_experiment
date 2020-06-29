package database.part.db2.controller;

import database.part.db2.dto.GradeWithCourse;
import database.part.db2.mapper.StudentMapper;
import database.part.db2.mapper.TeacherMapper;
import database.part.db2.mapper.auth.UserMapper;
import database.part.db2.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller

public class StudentGradeController {

    @Autowired
    IStudentService service;


    @GetMapping("/studentGrade")
    String studentGrade(Model model, HttpServletRequest request){
        //获取权限
        Authentication au_2 = SecurityContextHolder.getContext().getAuthentication();
        String username = service.getInfo(au_2).getName();
        //获取课程列表
        List<GradeWithCourse> ls = service. getGradeList(au_2);
        //上传
        model.addAttribute("gradeList", ls);
        model.addAttribute("username", username);

        return "studentGrade";

    }



}