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
            "计算机学院","网络空间安全学院","人工智能学院","软件学院","自动化学院",
            "现代邮政学院","光电信息学院","经济管理学院","理学院","人文学院",
            "数字媒体与设计艺术学院","马克思主义学院","国际学院","网络教育学院",
            "继续教育学院","叶培大创新创业学院");

    @GetMapping("/managerStudent")
    public String getStudentList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("collegeList", collegeList);
        List<StudentInfo> studentList = service.getStudentInfoList();
        session.setAttribute("studentList", studentList);
        return "managerStudent";
    }

    @PostMapping("/managerStudent/managerSelectStudent")
    public String getStudentListByCollege(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<StudentInfo> studentList = service.getStudentListByCollege((String)session.getAttribute("selectCollege"));
        session.setAttribute("studentList", studentList);
        return "managerStudent";
    }

    @PostMapping("/managerStudent/managerChangeStudent")
    public String changeStudent(HttpServletRequest request) {
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

    @PostMapping("/managerStudent/managerInputStudent")
    public String inputStudent(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Student student = new Student();
        student.setId((Long)session.getAttribute("id"));
        student.setClassId((Long)session.getAttribute("class"));
        student.setName((String)session.getAttribute("name"));
        student.setSex((String)session.getAttribute("sex"));
        student.setEmail((String)session.getAttribute("email"));

        service.inputStudent(student);
        return "managerStudent";
    }

    @DeleteMapping("/managerStudent/managerDeleteStudent")
    public String deleteStudent(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Student student = (Student)session.getAttribute("student");
        service.deleteStudent(student.getId());
        return "managerStudent";
    }

    @GetMapping("/managerTeacher")
    public String getTeacherList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("collegeList", collegeList);
        List<Teacher> teacherList = service.getTeacherList();
        session.setAttribute("teacherList", teacherList);
        return "managerTeacher";
    }

    @PostMapping("/managerTeacher/managerSelectTeacher")
    public String getTeacherListByCollege(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Teacher> teacherList = service.getTeacherListByCollege((String)session.getAttribute("selectCollege"));
        session.setAttribute("teacherList", teacherList);
        return "managertTeacher";
    }

    @PostMapping("/managerTeacher/managerChangeTeacher")
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

    @PostMapping("/managerTeacher/managerInputTeacher")
    public String inputTeacher(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Teacher teacher = new Teacher();
        teacher.setId((long)session.getAttribute("id"));
        teacher.setCollege((String)session.getAttribute("college"));
        teacher.setName((String)session.getAttribute("name"));
        teacher.setSex((String)session.getAttribute("sex"));
        teacher.setEmail((String)session.getAttribute("email"));

        service.inputTeacher(teacher);
        return "managerTeacher";
    }

    public String deleteTeacher(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher)session.getAttribute("teacher");
        service.deleteTeacher(teacher.getId());
        return "managerTeacher";
    }

    @GetMapping("/managerCourse")
    public String getCourseList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("collegeList", collegeList);
        List<CourseInfo> courseList = service.getCourseInfoList();
        session.setAttribute("courseList", courseList);
        return "managerCourse";
    }

    @PostMapping("/managerCourse/managerSelectCourse")
    public String getCourseListByCollege(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<CourseInfo> courseList = service.getCourseListByCollege((String)session.getAttribute("selectCollege"));
        session.setAttribute("courseList", courseList);
        return "managertCourse";
    }

    @PostMapping("/managerCourse/managerChangeCourse")
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

    @PostMapping("/managerCourse/managerInputCourse")
    public String inputCourse(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Course course = new Course();
        course.setId((long)session.getAttribute("id"));
        course.setTeacherId((Long)session.getAttribute("teacherId"));

        service.inputCourse(course);
        return "managerCourse";
    }

    public String deleteCourse(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Course course = (Course)session.getAttribute("course");
        service.deleteCourse(course.getId());
        return "managerCourse";
    }
}

