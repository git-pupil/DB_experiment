package database.part.db2.controller;

import database.part.db2.dto.StudentWithGrade;
import database.part.db2.entity.Course;
import database.part.db2.entity.Grade;
import database.part.db2.entity.Student;
import database.part.db2.entity.Teacher;
import database.part.db2.entity.auth.User;
import database.part.db2.mapper.GradeMapper;
import database.part.db2.mapper.StudentMapper;
import database.part.db2.mapper.TeacherMapper;
import database.part.db2.mapper.auth.UserMapper;
import database.part.db2.service.ITeacherService;
import database.part.db2.service.impl.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TeacherLookGradeController {
    @Autowired
    TeacherService service;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    GradeMapper gradeMapper;
    @Autowired
    UserMapper userMapper;

    /**
     * 教师主页面
     * @param model
     * @param request
     * @return "teacherHome"
     */

    @GetMapping("/teacherHome")
    String teacherHome(Model model, HttpServletRequest request) {
        //获取权限
        Authentication au = SecurityContextHolder.getContext().getAuthentication();
        Teacher t_info = service.getInfo(au);
        String username = t_info.getName();

        //上传个人信息
        model.addAttribute("username", username);
        //session.setAttribute("teacher", t_info);
        //上传
        model.addAttribute("teacher", t_info);

        return "teacherHome";
    }

    /**
     * 教师查看某一科目成绩
     * @param model
     * @param courseId
     * @return teacherLookGrade
     */

    @GetMapping("/teacherLookGrade")
    String teacherLookGrade(Model model, Long courseId){
        //获取权限
        Authentication au = SecurityContextHolder.getContext().getAuthentication();
        Teacher t_info = service.getInfo(au);
        String username = t_info.getName();
        model.addAttribute("username", username);

        //查找course对象并上传
        Course course = service.findCourse(courseId);
        model.addAttribute("course", course);

        //获取成绩信息
        List<StudentWithGrade> swg = service.getStudentList(courseId);

        //上传信息
        model.addAttribute("pointList", swg);

        return "teacherLookGrade";
    }

    /**
     * 由主页面到添加学生页面跳转
     * @param model
     * @param courseId
     * @return teacherAddStudent
     */

    @RequestMapping("/AddStudent")
    String AddStudent(Model model, Long courseId){
        //跳转并上传展示相关信息
        Course course = service.findCourse(courseId);
        System.out.println(course);
        model.addAttribute("course", course);
        return "teacherAddStudent";
    }

    /**
     * 教师给某门课添加学生：输入学号
     * @param model
     * @param courseId
     * @param id(表单数据)
     * @return teacherAddStudent
     */

    @RequestMapping("/teacherAddStudent")
    String teacherAddStudent(Model model, Long courseId, @RequestParam("id") Long id){
        //获取权限
        Authentication au = SecurityContextHolder.getContext().getAuthentication();
        Teacher t_info = service.getInfo(au);
        String username = t_info.getName();
        model.addAttribute("username", username);
        //System.out.println("进来添加了");
        Course course = service.findCourse(courseId);
        //当前grade表条目数 以获取新的id
        Long num = Long.valueOf(service.count());
        model.addAttribute("course",course);
        List<Course> lc = service.getCourseList(au);
        List<StudentWithGrade> swg = service.getStudentList(courseId);
        //上传
        model.addAttribute("courseList", lc);
        //设置grade信息
        User user = userMapper.findByUsername(Long.toString(id));
        int i;
        if(user != null)
        {
            if(swg != null)
                for (i = 0; i < swg.size() && id != swg.get(i).getId(); i++)
            if(i < swg.size())
            {
                //System.out.println("这个用户已经在课程列表中");
                model.addAttribute("addfailure", 1);
                return "teacherAddStudent";
            }
            else
            {
                //System.out.println("查询到了用户可以添加");
                Grade grade = new Grade(id, courseId);
                grade.setId(++num);
                //数据库新增
                gradeMapper.create(grade);
                return "teacherCourse";
            }
        }
        else
        {
            //System.out.println("没有这个用户");
            model.addAttribute("addfailure", 1);
            return "teacherAddStudent";
        }
        return "teacherCourse";
    }

    /**
     * 由教师主页面到修改某科目同学成绩页面跳转
     * @param model
     * @param studentId
     * @param courseId
     * @return teacherChangeGrade
     */

    @RequestMapping("/ChangeGrade")
    String ChangeGrade(Model model, Long studentId,  Long courseId){
        //获取权限信息
        Authentication au = SecurityContextHolder.getContext().getAuthentication();
        Teacher t_info = service.getInfo(au);
        String username = t_info.getName();
        model.addAttribute("username", username);

        //查询课程对象
        Course course = service.findCourse(courseId);
        model.addAttribute("course", course);

        //查找学生当前成绩
        Student student = studentMapper.findById(studentId);
        Grade grade = service.findGrade(studentId,courseId);

        //上传并展示相关信息
        model.addAttribute("grade", grade);
        model.addAttribute("student",student);
        return "teacherChangeGrade";
    }

    /**
     * 教师修改成绩
     * @param model
     * @param studentId
     * @param courseId
     * @param newPoint
     * @return teacherChangeGrade
     */

    @RequestMapping("/teacherChangeGrade")
    String teacherChangeGrade(Model model, Long studentId, Long courseId, @RequestParam("newPoint") int newPoint){
        System.out.println(newPoint);
        //获取权限信息
        Authentication au = SecurityContextHolder.getContext().getAuthentication();
        Teacher t_info = service.getInfo(au);
        String username = t_info.getName();
        model.addAttribute("username", username);

        //查找当前课程
        Course course = service.findCourse(courseId);
        model.addAttribute("course", course);

        //查找学生当前成绩
        System.out.println(studentId);
        System.out.println(courseId);
        Student student = studentMapper.findById(studentId);
        Grade grade = service.findGrade(studentId,courseId);
        System.out.println(grade);

        //上传相关信息
        model.addAttribute("grade", grade);
        model.addAttribute("student",student);
        //更新
        grade.setPoint(newPoint);
        System.out.println(grade);
        gradeMapper.update(grade);

        //获取成绩信息
        List<StudentWithGrade> swg = service.getStudentList(courseId);

        //上传信息
        model.addAttribute("pointList", swg);

        return "teacherLookGrade";
    }

    /**
     * 由教师主页面到录入成绩页面跳转
     * @param model
     * @param courseId
     * @return teacherInputGrade
     */
    @RequestMapping("/InputGrade")
    String teacherInputGrade(Model model,  Long courseId){
        //获取权限信息
        Authentication au = SecurityContextHolder.getContext().getAuthentication();
        Teacher t_info = service.getInfo(au);
        String username = t_info.getName();
        model.addAttribute("username", username);

        //查找当前课程
        Course course = service.findCourse(courseId);
        System.out.println(course.getName());
        model.addAttribute("course", course);

        //获取学生列表并上传显示
        List<StudentWithGrade> SWGs = service.getStudentList(courseId);
        model.addAttribute("gradeList", SWGs);

        return "teacherInputGrade";
    }

    /**
     * 录入成绩
     * @param model
     * @param courseId
     * @param ids
     * @param points
     * @return teacherInputGrade
     */
    @RequestMapping("/teacherInputGrade")
    String teacherInputGrade(Model model,  Long courseId, @RequestParam(value = "ids[]") ArrayList<Long> ids, @RequestParam(value = "points[]") ArrayList<Integer> points){
        //录入成绩数
        int cnt = ids.size();

        //获取权限信息
        Authentication au = SecurityContextHolder.getContext().getAuthentication();
        Teacher t_info = service.getInfo(au);
        String username = t_info.getName();
        model.addAttribute("username", username);
        //查找课程
        Course course = service.findCourse(courseId);
        model.addAttribute("course", course);

        //获取学生列表并上传显示
        List<StudentWithGrade> SWGs = service.getStudentList(courseId);
        model.addAttribute("gradeList", SWGs);

        //找到相应的grade 录入
        for (int i = 0; i <cnt; i++) {
            Grade grade = gradeMapper.findByStudentIdAndCourseId(ids.get(i),courseId);
            grade.setPoint(points.get(i));
            gradeMapper.update(grade);
        }
        //获取成绩信息
        List<StudentWithGrade> swg = service.getStudentList(courseId);

        //上传信息
        model.addAttribute("pointList", swg);

        return "teacherLookGrade";

    }

    @RequestMapping("/changePassword")
    String changePsw(Model model) {
        return "userChangePassword";

    }

    @RequestMapping("/userChangePassword")
    String changePassword(Model model, @RequestParam(value = "password") String password, @RequestParam(value = "password1") String psw1, @RequestParam(value = "password2") String psw2) {
        Authentication au = SecurityContextHolder.getContext().getAuthentication();
        String usn = au.getName();
        User user = userMapper.findByUsername(usn);
        System.out.println(user.getPassword());
        System.out.println(password);
        if(user.getPassword().equals(password))
        {
            System.out.println(1);
            if(psw1 == null || psw2 == null)
            {
                model.addAttribute("ErrorMsg2","请输入新密码");
                return "userChangePassword";
            }
            else if(psw1.equals(psw2))
            {
                user.setPassword(psw1);
                userMapper.update(user);
                return "redirect:/logout";
            }
            else {
                model.addAttribute("ErrorMsg3", "两次密码输入不一致");
                return "userChangePassword";
                }
        }
        else
            {
            model.addAttribute("ErrorMsg1", "原始密码错误，请重新输入");
            return "userChangePassword";
            }


    }

}
