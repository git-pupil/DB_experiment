package database.part.db2.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;


import database.part.db2.dto.CourseInfo;
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
import database.part.db2.service.IManagerService;
import database.part.db2.service.impl.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/managerHome")
public class ManagerController {

    @Autowired
    ManagerService service;

    List<String> collegeList = Arrays.asList("信息与通信工程","电子工程",
            "计算机","网络空间安全","人工智能","软件","自动化",
            "现代邮政","光电信息","经济管理","理","人文",
            "数字媒体与设计艺术","马克思主义","国际","网络教育",
            "继续教育","叶培大创新创业");

    @GetMapping("/managerStudent")
    public String getStudentList(Model model) {

        model.addAttribute("collegeList", collegeList);

        List<StudentInfo> studentList = service.getStudentInfoList();
        model.addAttribute("studentList", studentList);
        model.addAttribute("selectedCollege","全部学院");

        return "managerStudent";
    }

    @PostMapping("/managerSelectStudent")
    public String getStudentListByCollege(Model model,@RequestParam String selectCollege) {
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("collegeList", collegeList);

        List<StudentInfo> studentList = service.getStudentListByCollege(selectCollege);
        model.addAttribute("studentList", studentList);
        model.addAttribute("selectedCollege",selectCollege);

        return "managerStudent";
    }

    @GetMapping("/managerBeginChangeStudent")
    public String beginChangeStudent(@RequestParam Long id, Model model){
        //根据id返回student实体
        StudentInfo student = service.getStudentInfoById(id);
        model.addAttribute("student",student);
        return "managerChangeStudent";
    }

    @PostMapping("/managerChangeStudent")
    public String changeStudent(HttpServletRequest request, Model model, @RequestParam Long id,
                                @RequestParam Long classId, @RequestParam String name,
                                @RequestParam String sex, @RequestParam String email) {
        request.setAttribute("existedClass","1");
        Student student = new Student();

        student.setId(id);
        student.setClassId(classId);
        student.setName(name);
        student.setSex(sex);
        student.setEmail(email);

        if(service.getClassById(classId)==null){
            model.addAttribute("student",service.getStudentInfoById(id));
            request.setAttribute("existedClass","0");
            return "managerChangeStudent";
        }

        service.changeStudent(student);

        model.addAttribute("collegeList", collegeList);

        List<StudentInfo> studentList = service.getStudentInfoList();
        model.addAttribute("studentList", studentList);
        model.addAttribute("selectedCollege","全部学院");

        return "managerStudent";
    }

    @GetMapping("/managerBeginInputStudent")
    public String beginInputStudent(){
        return "managerInputStudent";
    }

    @PostMapping("/managerInputStudent")
    public String inputStudent(HttpServletRequest request, Model model, @RequestParam Long id,
                               @RequestParam Long classId, @RequestParam String name,
                               @RequestParam String sex, @RequestParam String email) {
        request.setAttribute("existedStudent", "0");
        request.setAttribute("existedClass","1");

        Student student = new Student();

        student.setId(id);
        student.setClassId(classId);
        student.setName(name);
        student.setSex(sex);
        student.setEmail(email);

        if(service.getClassById(classId)==null){
            request.setAttribute("existedClass","0");
            return "managerInputStudent";
        }

        if(service.inputStudent(student)==null){
            request.setAttribute("existedStudent", "1");
            return "managerInputStudent";
        }

        model.addAttribute("collegeList", collegeList);

        List<StudentInfo> studentList = service.getStudentInfoList();
        model.addAttribute("studentList", studentList);
        model.addAttribute("selectedCollege","全部学院");

        return "managerStudent";
    }

    @GetMapping("/managerDeleteStudent")
    public String deleteStudent(Model model, @RequestParam Long id) {
        service.deleteStudent(id);

        model.addAttribute("collegeList", collegeList);

        List<StudentInfo> studentList = service.getStudentInfoList();
        model.addAttribute("studentList", studentList);
        model.addAttribute("selectedCollege","全部学院");

        return "managerStudent";
    }

    @GetMapping("/managerTeacher")
    public String getTeacherList(Model model) {
        model.addAttribute("collegeList", collegeList);

        List<Teacher> teacherList = service.getTeacherList();
        model.addAttribute("teacherList", teacherList);
        model.addAttribute("selectedCollege","全部学院");
        return "managerTeacher";
    }

    @PostMapping("/managerSelectTeacher")
    public String getTeacherListByCollege(@RequestParam String selectCollege, Model model) {
        model.addAttribute("collegeList", collegeList);

        List<Teacher> teacherList = service.getTeacherListByCollege(selectCollege);
        model.addAttribute("teacherList", teacherList);
        model.addAttribute("selectedCollege",selectCollege);
        return "managerTeacher";
    }

    @GetMapping("/managerBeginChangeTeacher")
    public String beginChangeTeacher(@RequestParam Long id, Model model){
        //根据id返回teacher实体
        Teacher teacher = service.getTeacherById(id);
        model.addAttribute("teacher",teacher);

        model.addAttribute("collegeList", collegeList);
        model.addAttribute("selectedCollege",teacher.getCollege());
        return "managerChangeTeacher";
    }

    @PostMapping("/managerChangeTeacher")
    public String changeTeacher(Model model, @RequestParam Long id, @RequestParam String college,
                                @RequestParam String name, @RequestParam String sex,
                                @RequestParam String email) {
        Teacher teacher = new Teacher();

        teacher.setId(id);
        teacher.setCollege(college);
        teacher.setName(name);
        teacher.setSex(sex);
        teacher.setEmail(email);

        service.changeTeacher(teacher);

        model.addAttribute("collegeList", collegeList);

        List<Teacher> teacherList = service.getTeacherList();
        model.addAttribute("teacherList", teacherList);
        model.addAttribute("selectedCollege","全部学院");

        return "managerTeacher";
    }

    @GetMapping("/managerBeginInputTeacher")
    public String beginInputTeacher(Model model){
        model.addAttribute("collegeList", collegeList);
        model.addAttribute("selectedCollege","请选择学院");
        return "managerInputTeacher";
    }

    @PostMapping("/managerInputTeacher")
    public String inputTeacher(HttpServletRequest request, Model model,
                               @RequestParam Long id, @RequestParam String college,
                               @RequestParam String name, @RequestParam String sex,
                               @RequestParam String email) {
        request.setAttribute("existedTeacher", "0");
        Teacher teacher = new Teacher();

        teacher.setId(id);
        teacher.setCollege(college);
        teacher.setName(name);
        teacher.setSex(sex);
        teacher.setEmail(email);

        model.addAttribute("collegeList", collegeList);

        if(service.inputTeacher(teacher)==null){
            model.addAttribute("selectedCollege","请选择学院");
            request.setAttribute("existedTeacher", "1");
            return "managerInputTeacher";
        }

        model.addAttribute("selectedCollege","全部学院");
        List<Teacher> teacherList = service.getTeacherList();
        model.addAttribute("teacherList", teacherList);

        return "managerTeacher";
    }

    @GetMapping("/managerDeleteTeacher")
    public String deleteTeacher(Model model, @RequestParam Long id) {
        service.deleteTeacher(id);

        model.addAttribute("collegeList", collegeList);

        List<Teacher> teacherList = service.getTeacherList();
        model.addAttribute("teacherList", teacherList);
        model.addAttribute("selectedCollege","全部学院");

        return "managerTeacher";
    }

    @GetMapping("/managerCourse")
    public String getCourseList(Model model) {
        model.addAttribute("collegeList", collegeList);
        List<CourseInfo> courseList = service.getCourseInfoList();
        model.addAttribute("courseList", courseList);
        model.addAttribute("selectedCollege","全部学院");
        return "managerCourse";
    }

    @PostMapping("/managerSelectCourse")
    public String getCourseListByCollege (Model model,@RequestParam String selectCollege){
        model.addAttribute("collegeList", collegeList);
        List<CourseInfo> courseList = service.getCourseListByCollege(selectCollege);
        model.addAttribute("courseList", courseList);
        model.addAttribute("selectedCollege",selectCollege);
        return "managerCourse";
    }

    @GetMapping("/managerBeginChangeCourse")
    public String beginChangeCourse(@RequestParam Long id, Model model){
        //根据id返回course实体
        CourseInfo course = service.getCourseInfoById(id);
        model.addAttribute("course",course);

        return "managerChangeCourse";
    }

    @PostMapping("/managerChangeCourse")
    public String changeCourse(HttpServletRequest request, Model model, @RequestParam Long id,
                                @RequestParam String name, @RequestParam Long teacherId) {
        request.setAttribute("existedTeacher","1");
        Course course = new Course();

        course.setId(id);
        course.setName(name);
        course.setTeacherId(teacherId);

        if(service.getTeacherById(teacherId)==null){
            model.addAttribute("course",service.getCourseInfoById(id));
            request.setAttribute("existedTeacher","0");
            return "managerChangeCourse";
        }

        service.changeCourse(course);

        model.addAttribute("collegeList", collegeList);

        List<CourseInfo> courseList = service.getCourseInfoList();
        model.addAttribute("courseList", courseList);
        model.addAttribute("selectedCollege","全部学院");

        return "managerCourse";
    }

    @GetMapping("/managerBeginInputCourse")
    public String beginInputCourse(){
        return "managerInputCourse";
    }

    @PostMapping("/managerInputCourse")
    public String inputCourse(HttpServletRequest request, Model model, @RequestParam Long id,
                              @RequestParam Long teacherId,@RequestParam String name) {
        request.setAttribute("existedCourse", "0");
        request.setAttribute("existedTeacher","1");

        Course course = new Course();
        course.setId(id);
        course.setTeacherId(teacherId);
        course.setName(name);

        if(service.getTeacherById(teacherId)==null){
            request.setAttribute("existedTeacher","0");
            return "managerInputCourse";
        }

        if(service.inputCourse(course)==null){
            request.setAttribute("existedCourse", "1");
            return "managerInputCourse";
        }

        model.addAttribute("collegeList", collegeList);
        List<CourseInfo> courseList = service.getCourseInfoList();
        model.addAttribute("courseList", courseList);
        model.addAttribute("selectedCollege","全部学院");

        return "managerCourse";
    }

    @GetMapping("/managerDeleteCourse")
    public String deleteCourse(Model model, @RequestParam Long id) {
        service.deleteCourse(id);
        model.addAttribute("collegeList", collegeList);
        List<CourseInfo> courseList = service.getCourseInfoList();
        model.addAttribute("courseList", courseList);
        model.addAttribute("selectedCollege","全部学院");
        return "managerCourse";
    }
}

