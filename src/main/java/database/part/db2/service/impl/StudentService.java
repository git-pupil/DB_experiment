package database.part.db2.service.impl;

import database.part.db2.dto.GradeWithCourse;
import database.part.db2.dto.StudentInfo;
import database.part.db2.entity.Course;
import database.part.db2.entity.Student;
import database.part.db2.entity.auth.User;
import database.part.db2.mapper.CourseMapper;
import database.part.db2.mapper.StudentMapper;
import database.part.db2.mapper.auth.UserMapper;
import database.part.db2.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements IStudentService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    UserMapper userMapper;


    @Override
    public StudentInfo getStuInfo(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());//获取当前登录用户的Id
        StudentInfo StuInfo = studentMapper.findInfoById(userId);
        return StuInfo;
    }


    @Override
    public List<GradeWithCourse> getGradeList(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());//获取当前登录用户的Id
        List<GradeWithCourse> gradeList = courseMapper.findGradeById(userId);
        return gradeList;
    }

    @Override
    public Student getInfo(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());//获取当前登录用户的Id
        Student currentStudent = studentMapper.findById(userId);
        return currentStudent;
    }

    @Override
    public int changePassword(Authentication authentication,String password) {
        String username = authentication.getName();//获取当前登录用户的Id
        User user = userMapper.findByUsername(username);
        user.setPassword(password);
        return userMapper.update(user);
    }

}
