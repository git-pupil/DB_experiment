package database.part.db2.service.impl;

import database.part.db2.dto.GradeWithCourse;
import database.part.db2.dto.StudentWithGrade;
import database.part.db2.entity.Course;
import database.part.db2.entity.Grade;
import database.part.db2.entity.Student;
import database.part.db2.entity.Teacher;
import database.part.db2.entity.auth.User;
import database.part.db2.mapper.CourseMapper;
import database.part.db2.mapper.GradeMapper;
import database.part.db2.mapper.StudentMapper;
import database.part.db2.mapper.TeacherMapper;
import database.part.db2.mapper.auth.UserMapper;
import database.part.db2.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService implements ITeacherService {
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    GradeMapper gradeMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public Course findCourse(Long courseId){

        return courseMapper.findById(courseId);
    }
    //分数段人数统计
    @Override
    public int countStage(Long courseId,int low,int high)
    {
        int cnt = gradeMapper.countStage(courseId,low,high);
        return cnt;

    }
    //查看grade表人数，获取id
    @Override
    public int count()
    {
        int cnt = gradeMapper.count();
        return cnt;

    }
    //查找某个学生的成绩
    @Override
    public Grade findGrade(Long studentId,Long courseId)
    {
        return gradeMapper.findByStudentIdAndCourseId(studentId,courseId);
    }


    @Override
    public List<StudentWithGrade> getStudentList(Long courseId) {
        List<StudentWithGrade> gradeList = studentMapper.findGradeById(courseId);
        return gradeList;
    }

    @Override
    public Teacher getInfo(Authentication authentication) {
        //获取当前登录用户的Id
        Long userId = Long.valueOf(authentication.getName());
        return teacherMapper.findById(userId);
    }

    @Override
    public List<Course> getCourseList(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());//获取当前登录用户的Id
        List<Course> courseList = courseMapper.findByTeacherId(userId);
        return courseList;
    }

    @Override
    public List<StudentWithGrade> setGrade(Long courseId, List<StudentWithGrade> studentList) {
        for(StudentWithGrade studentWithGrade : studentList){
            Grade grade = gradeMapper.findByStudentIdAndCourseId(studentWithGrade.getId(),courseId);
            grade.setPoint(studentWithGrade.getPoint());
            gradeMapper.update(grade);
        }
        return studentMapper.findGradeById(courseId);
    }

    @Override
    public int changePassword(Authentication authentication,String password) {
        String username = authentication.getName();//获取当前登录用户的Id
        User user = userMapper.findByUsername(username);
        user.setPassword(password);
        return userMapper.update(user);
    }

    @Override
    public Grade inputStudent(Long studentId, Long courseId) {
        Grade grade = new Grade(studentId,courseId);
        gradeMapper.create(grade);
        return gradeMapper.findByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    public int deleteStudent(Long studentId, Long courseId) {
        Grade grade = gradeMapper.findByStudentIdAndCourseId(studentId, courseId);
        return gradeMapper.deleteById(grade.getId());
    }
}
