package database.part.db2.service;

import database.part.db2.dto.GradeWithCourse;
import database.part.db2.dto.StudentInfo;
import database.part.db2.entity.Student;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IStudentService {
    //获取显示基本信息
    public StudentInfo getStuInfo(Authentication authentication);


    //获取成绩列表，需要输入当前用户id
    public List<GradeWithCourse> getGradeList(Authentication authentication);
    //获取基本信息
    public Student getInfo(Authentication authentication);

    public int changePassword(Authentication authentication, String password);
}
