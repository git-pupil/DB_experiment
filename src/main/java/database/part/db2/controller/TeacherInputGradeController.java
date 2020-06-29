package database.part.db2.controller;

import database.part.db2.dto.StudentInfo;
import database.part.db2.dto.StudentWithGrade;
import database.part.db2.entity.Course;
import database.part.db2.entity.Grade;
import database.part.db2.entity.Teacher;
import database.part.db2.mapper.GradeMapper;
import database.part.db2.service.ITeacherService;
import database.part.db2.service.impl.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
@Controller
public class TeacherInputGradeController {
    @Autowired
    GradeMapper gradeMapper;
    @Autowired
    ITeacherService service;
    //获取相关权限
    Authentication au = SecurityContextHolder.getContext().getAuthentication();

    Teacher t_info = service.getInfo(au);
    String username = t_info.getName();

    @PostMapping("/teacherInputGrade")
    String teacherInputGrade(Model model, HttpServletRequest request, Long courseId,
                             @RequestParam(value = "ids[]") ArrayList<Long> ids, @RequestParam(value = "points[]") ArrayList<Integer> points){
        //录入成绩数
        int cnt = ids.size();
        //上传相关信息
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        Course course = service.findCourse(courseId);
        session.setAttribute("course", course);
        //获取学生列表并上传显示
        List<StudentWithGrade> SWGs = service.getStudentList(courseId);
        session.setAttribute("gradeList", SWGs);
        //找到相应的grade 录入
        for (int i = 0; i <cnt; i++) {
            Grade grade = gradeMapper.findByStudentIdAndCourseId(ids.get(i),courseId);
            grade.setPoint(points.get(i));
            gradeMapper.update(grade);
        }

        return "teacherLookGrade";
    }

}
