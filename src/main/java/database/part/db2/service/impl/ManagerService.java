package database.part.db2.service.impl;

import database.part.db2.dto.CourseInfo;
import database.part.db2.dto.StudentInfo;
import database.part.db2.entity.Course;
import database.part.db2.entity.Student;
import database.part.db2.entity.Teacher;
import database.part.db2.entity.auth.User;
import database.part.db2.mapper.CourseMapper;
import database.part.db2.mapper.GradeMapper;
import database.part.db2.mapper.StudentMapper;
import database.part.db2.mapper.TeacherMapper;
import database.part.db2.mapper.auth.UserMapper;
import database.part.db2.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ManagerService implements IManagerService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    GradeMapper gradeMapper;
    @Autowired
    UserMapper userMapper;

    public List<StudentInfo> getStudentListByCollege(String college) {
        return studentMapper.findInfoByCollege(college);
    }
    public List<Teacher> getTeacherListByCollege(String college){
        return teacherMapper.findByCollege(college);
    }

    public List<CourseInfo> getCourseListByCollege(String college){return courseMapper.findInfoByCollege(college);}

    @Override
    public List<Student> getStudentList() {
        return studentMapper.findAll();
    }
    @Override
    public List<StudentInfo> getStudentInfoList() {
        return studentMapper.findInfo();
    }

    @Override
    public List<Teacher> getTeacherList() {
        return teacherMapper.findAll();
    }

    @Override
    public List<Course> getCourseList() {
        return courseMapper.findAll();
    }
    @Override
    public List<CourseInfo> getCourseInfoList() {
        return courseMapper.findInfo();
    }

    @Override
    public Student changeStudent(Student student) {
        studentMapper.update(student);
        return studentMapper.findById(student.getId());
    }

    @Override
    public Teacher changeTeacher(Teacher teacher) {
        teacherMapper.update(teacher);
        return teacherMapper.findById(teacher.getId());
    }

    @Override
    public Course changeCourse(Course course) {
        courseMapper.update(course);
        return courseMapper.findById(course.getId());
    }

    @Override
    @Transactional
    public int deleteStudent(Long studentId) {
        userMapper.deleteByUsername(studentId.toString());
        gradeMapper.deleteByStudentId(studentId);
        return studentMapper.deleteById(studentId);
    }

    @Override
    @Transactional
    public int deleteTeacher(Long teacherId) {
        userMapper.deleteByUsername(teacherId.toString());
        List<Course> courseList = courseMapper.findByTeacherId(teacherId);
        for(Course course : courseList) {
            gradeMapper.deleteByCourseId(course.getId());
            courseMapper.deleteById(course.getId());
        }
        return teacherMapper.deleteById(teacherId);
    }

    @Override
    public int deleteCourse(Long courseId) {
        return gradeMapper.deleteByCourseId(courseId);
    }

    @Override
    public Student inputStudent(Student student) {
        Student currentStudent = studentMapper.findById(student.getId());
        if (currentStudent!=null) return null;
        else {
            studentMapper.create(student);
            return student;
        }
    }

    @Override
    public Teacher inputTeacher(Teacher teacher) {
        Teacher currentTeacher = teacherMapper.findById(teacher.getId());
        if (currentTeacher!=null) return null;
        else {
            teacherMapper.create(teacher);
            return teacher;
        }
    }

    @Override
    public Course inputCourse(Course course) {
        Course currentCourse = courseMapper.findById(course.getId());
        if (currentCourse!=null) return null;
        else {
            courseMapper.create(course);
            return course;
        }
    }

    @Override
    public int changePassword(Authentication authentication, String password) {
        String username = authentication.getName();//获取当前登录用户的Id
        User user = userMapper.findByUsername(username);
        user.setPassword(password);
        return userMapper.update(user);
    }
    @Override
    public int changePassword(Long id, String password){
        User user = userMapper.findByUsername(id.toString());
        user.setPassword(password);
        return userMapper.update(user);
    }
}
