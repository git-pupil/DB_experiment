package database.part.db2.service;

import database.part.db2.dto.StudentWithGrade;
import database.part.db2.entity.Course;
import database.part.db2.entity.Grade;
import database.part.db2.entity.Teacher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ITeacherService {
    //获取某门课所有学生成绩
    public List<StudentWithGrade> getStudentList(Long courseId);
    //获取基本信息
    public Teacher getInfo(Authentication authentication);
    //获取所授课程信息
    public List<Course> getCourseList(Authentication authentication);
    //录入或修改成绩
    public List<StudentWithGrade> setGrade(Long courseId, List<StudentWithGrade> studentList);
    //修改密码
    public int changePassword(Authentication authentication, String password);
    //增加学生,添加成功返回学生成绩内容,学生不存在返回null
    public Grade inputStudent(Long studentId, Long courseId);
    //删除学生
    public int deleteStudent(Long studentId, Long courseId);


    //获取某课程信息
    public Course findCourse(Long courseId);
    //获取某学生成绩
    public Grade findGrade(Long studentId, Long courseId);
    //分数段人数统计
    public int countStage(Long courseId,int low,int high);
    //某门课程人数统计
    public int count();
}
