package database.part.db2.controller;

import database.part.db2.dto.StudentInfo;
import database.part.db2.service.IStudentService;
import database.part.db2.service.ITeacherService;
import database.part.db2.service.impl.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller

public class StudentController {

    @Autowired
    StudentService service;


    @GetMapping ("/studentHome")
    String studentHome(Model model, HttpServletRequest request) {
        //获取权限
        Authentication au_2 = SecurityContextHolder.getContext().getAuthentication();
        StudentInfo s_info = service.getStuInfo(au_2);
        String username = s_info.getName();
        model.addAttribute("username", username);
        //获取个人信息
        request.getSession().setAttribute("student", s_info);
        model.addAttribute("student", s_info);

        return "studentHome";
    }

}


