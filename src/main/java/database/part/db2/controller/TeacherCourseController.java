package database.part.db2.controller;


import database.part.db2.entity.Course;
import database.part.db2.entity.Teacher;
import database.part.db2.service.ITeacherService;
import database.part.db2.service.impl.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TeacherCourseController {
    @Autowired
    TeacherService service;

    /**
     * 教师查看课程列表
     * @param model
     * @param request
     * @return teacherCourse
     */

    @RequestMapping("/teacherCourse")
    String teacherCourse(Model model, HttpServletRequest request) {
        //获取相关权限
        Authentication au = SecurityContextHolder.getContext().getAuthentication();
        Teacher t_info = service.getInfo(au);

        //上传用户名
        String username = t_info.getName();
        model.addAttribute("username", username);
        //获取课程列表
        List<Course> lc = service.getCourseList(au);
        //session.setAttribute("courseList", lc);

        //上传
        model.addAttribute("courseList", lc);

        return "teacherCourse";

    }

    /**
     * 教师查看扇形图
     * @param model
     * @param courseId
     * @return teacherLookCharm
     */

    @RequestMapping("/teacherLookCharm")
    String teacherLookCharm(Model model, Long courseId){
        //获取相关权限
        Authentication au = SecurityContextHolder.getContext().getAuthentication();

        Teacher t_info = service.getInfo(au);
        String username = t_info.getName();
        //上传相关信息
        model.addAttribute("username", username);
        Course course = service.findCourse(courseId);
        model.addAttribute("course", course);
        //获取各分数段人数数据
        int num1,num2,num3,num4,num5,num6;
        num1 = service.countStage(courseId,0,40);
        num2 = service.countStage(courseId,40,60);
        num3 = service.countStage(courseId,60,70);
        num4 = service.countStage(courseId,70,80);
        num5 = service.countStage(courseId,80,90);
        num6 = service.countStage(courseId,90,100);
        model.addAttribute("num1", num1);
        model.addAttribute("num2", num2);
        model.addAttribute("num3", num3);
        model.addAttribute("num4", num4);
        model.addAttribute("num5", num5);
        model.addAttribute("num6", num6);
        return "teacherLookCharm";

    }

    /**
     * 教师查看直方图
     * @param model
     * @param courseId
     * @return teacherLookCharm1
     */

    @RequestMapping("/teacherLookCharm1")
    String teacherLookCharm1(Model model,  Long courseId){
        //获取相关权限
        Authentication au = SecurityContextHolder.getContext().getAuthentication();

        Teacher t_info = service.getInfo(au);
        String username = t_info.getName();

        model.addAttribute("username", username);
        Course course = service.findCourse(courseId);
        model.addAttribute("course", course);
        //获取各分数段人数数据
        int num1,num2,num3,num4,num5,num6;
        num1 = service.countStage(courseId,0,40);
        num2 = service.countStage(courseId,40,60);
        num3 = service.countStage(courseId,60,70);
        num4 = service.countStage(courseId,70,80);
        num5 = service.countStage(courseId,80,90);
        num6 = service.countStage(courseId,90,100);
        model.addAttribute("num1", num1);
        model.addAttribute("num2", num2);
        model.addAttribute("num3", num3);
        model.addAttribute("num4", num4);
        model.addAttribute("num5", num5);
        model.addAttribute("num6", num6);


        return "teacherLookCharm1";

    }

}
