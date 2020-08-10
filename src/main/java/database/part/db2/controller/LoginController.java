package database.part.db2.controller;

import database.part.db2.dto.StudentInfo;
import database.part.db2.entity.Course;
import database.part.db2.entity.Manager;
import database.part.db2.entity.Student;
import database.part.db2.entity.Teacher;
import database.part.db2.entity.auth.User;
import database.part.db2.mapper.CourseMapper;
import database.part.db2.mapper.ManagerMapper;
import database.part.db2.mapper.StudentMapper;
import database.part.db2.mapper.TeacherMapper;
import database.part.db2.mapper.auth.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;

@Controller
public class LoginController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    ManagerMapper managerMapper;

    @GetMapping("/myLogin")
    public String login(){
        return "login";
    }
    @GetMapping("/myLogout")
    public String logout(){
        return "login";
    }
    @RequestMapping("/loginSuccess")
    public String loginSuccess(HttpServletRequest request){
        //request.setAttribute("logfailure", "0");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long userId = Long.valueOf(username);
        User user = userMapper.findByUsername(username);
        String Role = user.getRole();
        if (Role.equals("STUDENT")){
            StudentInfo studentInfo = studentMapper.findInfoById(userId);
            request.setAttribute("username", studentInfo.getName());
            request.setAttribute("student", studentInfo);
            return "studentHome";
        }else if (Role.equals("TEACHER")){
            Teacher teacher = teacherMapper.findById(userId);
            request.setAttribute("username", teacher.getName());
            request.setAttribute("teacher", teacher);
            return "teacherHome";
        }else{
            Manager manager = managerMapper.findById(userId);
            request.setAttribute("username", manager.getName());
            request.setAttribute("manager", manager);
            return "managerHome";
        }
    }
    @RequestMapping("logoutSuccess")
    public String logoutSuccess(){
        return "login";
    }
    @RequestMapping("/loginFailure")
    public String failure(HttpServletRequest request){
        //request.setAttribute("logfailure", "1");
        return "login";
    }
}
