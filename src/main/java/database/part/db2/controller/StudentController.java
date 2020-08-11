package database.part.db2.controller;

import database.part.db2.dto.GradeWithCourse;
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
import java.util.List;

@Controller

public class StudentController {

    @Autowired
    StudentService service;

    /**
     * 学生主页面
     * @param model
     * @param request
     * @return studentHome
     */

    @GetMapping ("/studentHome")
    String studentHome(Model model, HttpServletRequest request) {
        //获取权限
        Authentication au_2 = SecurityContextHolder.getContext().getAuthentication();
        StudentInfo s_info = service.getStuInfo(au_2);
        //查找用户名
        String username = s_info.getName();
        //获取个人信息
        request.getSession().setAttribute("student", s_info);
        //上传个人信息
        model.addAttribute("username", username);
        model.addAttribute("student", s_info);

        return "studentHome";
    }

    /**
     * 学生查询自己的成绩
     * @param model
     * @param request
     * @return studentGrade
     */

    @GetMapping("/studentGrade")
    String studentGrade(Model model, HttpServletRequest request){
        //获取权限
        Authentication au_2 = SecurityContextHolder.getContext().getAuthentication();
        String username = service.getInfo(au_2).getName();
        System.out.println(username);
        //获取课程列表
        List<GradeWithCourse> ls = service. getGradeList(au_2);
        //上传
        model.addAttribute("gradeList", ls);
        model.addAttribute("username", username);

        return "studentGrade";

    }

}


