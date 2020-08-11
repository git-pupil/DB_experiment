package database.part.db2.service;



import database.part.db2.dto.CourseInfo;
import database.part.db2.dto.StudentInfo;
import database.part.db2.entity.Class;
import database.part.db2.entity.Course;
import database.part.db2.entity.Manager;
import database.part.db2.entity.Student;
import database.part.db2.entity.Teacher;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface IManagerService {
    //获取显示基本信息
    public Manager getManagerInfo(Authentication authentication);

    public StudentInfo getStudentInfoById(Long id);

    public List<StudentInfo> getStudentInfoList();
    public List<StudentInfo> getStudentListByCollege(String college);
    public Teacher getTeacherById(Long id);
    public List<Teacher> getTeacherList();
    public List<Teacher> getTeacherListByCollege(String college);
    public List<CourseInfo> getCourseInfoList();
    public List<CourseInfo> getCourseListByCollege(String college);
    public CourseInfo getCourseInfoById(Long id);
    public Class getClassById(Long id);

    public List<Student> getStudentList();
    public List<Course> getCourseList();

    public Student changeStudent(Student student);
    public Teacher changeTeacher(Teacher teacher);
    public Course changeCourse(Course course);

    public int deleteStudent(Long studentId);
    public int deleteTeacher(Long teacherId);
    public int deleteCourse(Long courseId);

    //添加成功返回User,User已存在返回null
    public Student inputStudent(Student student);
    public Teacher inputTeacher(Teacher teacher);
    public Course inputCourse(Course course);

    public int changePassword(Authentication authentication, String password);
    public int changePassword(Long id, String password);
}
