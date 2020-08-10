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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/managerHome")
public class ManagerController {

    @Autowired
    ManagerService service;
    Authentication au = SecurityContextHolder.getContext().getAuthentication();
    List<String> collegeList = Arrays.asList("信息与通信工程学院","电子工程学院",
            "计算机","网络空间安全学院","人工智能学院","软件学院","自动化学院",
            "现代邮政学院","光电信息学院","经济管理学院","理学院","人文学院",
            "数字媒体与设计艺术学院","马克思主义学院","国际学院","网络教育学院",
            "继续教育学院","叶培大创新创业学院");

    @GetMapping("/managerStudent")
    public String getStudentList(Model model, HttpServletRequest request) {
        model.addAttribute("collegeList", collegeList);
        List<StudentInfo> studentList = service.getStudentInfoList();
        model.addAttribute("studentList", studentList);
        model.addAttribute("selectedCollege","全部学院");
        return "managerStudent";
    }

    @PostMapping("/managerSelectStudent")
    public String getStudentListByCollege(Model model,@RequestParam String selectCollege) {
        model.addAttribute("collegeList", collegeList);
        List<StudentInfo> studentList = service.getStudentListByCollege(selectCollege);
        model.addAttribute("studentList", studentList);
        model.addAttribute("selectedCollege",selectCollege);
        return "managerStudent";
    }

    @GetMapping("/managerBeginChangeStudent")
    public String beginChangeStudent(@RequestParam Long id, Model model){
        //根据id返回student实体
        return "managerChangeStudent";
    }

    @PostMapping("/managerChangeStudent")
    public String changeStudent(HttpServletRequest request,@RequestParam Long id) {
        HttpSession session = request.getSession();
        Student student = new Student();

        student.setId(((Student)session.getAttribute("student")).getId());
        Long classId = (Long)session.getAttribute("class");
        String name = (String)session.getAttribute("name");
        String sex = (String)session.getAttribute("sex");
        String email = (String)session.getAttribute("email");

        if( classId != null)
            student.setClassId(classId);
        else
            student.setClassId(((Student)session.getAttribute("student")).getClassId());
        if( name != null)
            student.setName(name);
        else
            student.setName(((Student)session.getAttribute("student")).getName());
        if( sex != null)
            student.setSex(sex);
        else
            student.setSex(((Student)session.getAttribute("student")).getSex());
        if( email != null)
            student.setEmail(email);
        else
            student.setEmail(((Student)session.getAttribute("student")).getEmail());

        service.changeStudent(student);
        return "managerStudent";
    }

    @GetMapping("/managerBeginInputStudent")
    public String beginInputStudent(){
        return "managerInputStudent";
    }

    @PostMapping("/managerInputStudent")
    public String inputStudent(Model model, HttpServletRequest request, @RequestParam Long id,
                               @RequestParam Long classid,
                               @RequestParam String name, @RequestParam String sex, @RequestParam String email) {
        HttpSession session = request.getSession();
        Student student = new Student();
        student.setId(id);
        student.setClassId(classid);
        student.setName(name);
        student.setSex(sex);
        student.setEmail(email);

        service.inputStudent(student);
        System.out.println(student);

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
    public String getTeacherList(HttpServletRequest request, Model model) {
        model.addAttribute("collegeList", collegeList);

        List<Teacher> teacherList = service.getTeacherList();
        model.addAttribute("teacherList", teacherList);
        model.addAttribute("selectedCollege","全部学院");
        return "managerTeacher";
    }

    @PostMapping("/managerSelectTeacher")
    public String getTeacherListByCollege(HttpServletRequest request,@RequestParam String selectCollege, Model model) {
        System.out.println(selectCollege);
        model.addAttribute("collegeList", collegeList);
        List<Teacher> teacherList = service.getTeacherListByCollege(selectCollege);
        model.addAttribute("teacherList", teacherList);
        model.addAttribute("selectedCollege",selectCollege);
        return "managerTeacher";
    }



    @PostMapping("/managerChangeTeacher")
    public String changeTeacher(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Teacher teacher = new Teacher();

        teacher.setId(((Teacher)session.getAttribute("teacher")).getId());
        String college = (String)session.getAttribute("college");
        String name = (String)session.getAttribute("name");
        String sex = (String)session.getAttribute("sex");
        String email = (String)session.getAttribute("email");


        if( college != null)
            teacher.setCollege(college);
        else
            teacher.setCollege(((Teacher)session.getAttribute("teacher")).getCollege());
        if( name != null)
            teacher.setName(name);
        else
            teacher.setName(((Teacher)session.getAttribute("teacher")).getName());
        if( sex != null)
            teacher.setSex(sex);
        else
            teacher.setSex(((Teacher)session.getAttribute("teacher")).getSex());
        if( email != null)
            teacher.setEmail(email);
        else
            teacher.setEmail(((Teacher)session.getAttribute("teacher")).getEmail());

        service.changeTeacher(teacher);
        return "managerTeacher";
    }

    @GetMapping("/managerBeginInputTeacher")
    public String beginInputTeacher(){
        return "managerInputTeacher";
    }

    @PostMapping("/managerInputTeacher")
    public String inputTeacher(Model model, @RequestParam Long id,
                               @RequestParam String college,
                               @RequestParam String name, @RequestParam String sex, @RequestParam String email) {
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setCollege(college);
        teacher.setName(name);
        teacher.setSex(sex);
        teacher.setEmail(email);

        service.inputTeacher(teacher);
        model.addAttribute("collegeList", collegeList);
        List<Teacher> teacherList = service.getTeacherList();
        model.addAttribute("teacherList", teacherList);
        model.addAttribute("selectedCollege","全部学院");
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

    @PostMapping("/managerChangeCourse")
    public String changeCourse(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Course course = new Course();

        course.setId(((Course)session.getAttribute("course")).getId());
        String name = (String)session.getAttribute("name");
        Long teacherId = (Long)session.getAttribute("teacherId");

        if( name != null)
            course.setName(name);
        else
            course.setName(((Course)session.getAttribute("course")).getName());
        if( teacherId != null)
            course.setTeacherId(teacherId);
        else
            course.setTeacherId(((Course)session.getAttribute("course")).getTeacherId());

        service.changeCourse(course);
        return "managerCourse";
    }

    @GetMapping("/managerBeginInputCourse")
    public String beginInputCourse(){
        return "managerInputCourse";
    }
    @PostMapping("/managerInputCourse")
    public String inputCourse(Model model, @RequestParam Long id,
                              @RequestParam Long teacherId,@RequestParam String name) {
        Course course = new Course();
        course.setId(id);
        course.setTeacherId(teacherId);
        course.setName(name);

        service.inputCourse(course);

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

