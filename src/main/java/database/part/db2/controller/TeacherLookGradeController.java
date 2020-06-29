package database.part.db2.controller;

import database.part.db2.dto.StudentWithGrade;
import database.part.db2.entity.Course;
import database.part.db2.entity.Teacher;
import database.part.db2.service.ITeacherService;
import database.part.db2.service.impl.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TeacherLookGradeController {
    @Autowired
    TeacherService service;
        //获取相关权限

    @GetMapping("/teacherLookGrade")
    String teacherLookGrade(Model model, HttpServletRequest request, Long courseId){
        Authentication au = SecurityContextHolder.getContext().getAuthentication();

        Teacher t_info = service.getInfo(au);
        String username = t_info.getName();


        model.addAttribute("username", username);
        Course course = service.findCourse(courseId);
        model.addAttribute("course", course);
        //获取成绩信息
        List<StudentWithGrade> swg = service.getStudentList(courseId);
        request.getSession().setAttribute("pointList", swg);
        model.addAttribute("pointList", swg);
        return "teacherLookGrade";
    }

}
