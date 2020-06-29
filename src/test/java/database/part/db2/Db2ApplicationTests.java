package database.part.db2;

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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class Db2ApplicationTests {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    GradeMapper gradeMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    TeacherMapper teacherMapper;

    @Test
    void course() {
        //某门课的成绩表
        //System.out.println(studentMapper.findGradeById((long)2));
        //某门课某学生成绩
        System.out.println(gradeMapper.findByStudentIdAndCourseId((long)2510, (long)2));

        //创建一门新的课程
        Course course = new Course();
        course.setId((long)4);
        course.setName("计网1");
        course.setTeacherId((long)1224);
        System.out.println(courseMapper.create(course));
    }

    @Test
    void student(){
        //学生成绩列表
        //System.out.println(courseMapper.findGradeById((long)2510));
        //学生基本信息
        System.out.println(studentMapper.findById((long)2521));
    }

    @Test
    void teacher(){
        //所教课程信息
        //System.out.println(courseMapper.findByTeacherId((long)1223));
        //修改某学生成绩
        StudentWithGrade studentWithGrade = new StudentWithGrade();
        studentWithGrade.setId((long)2510);
        studentWithGrade.setName("小孙");
        studentWithGrade.setPoint(100);
        Grade grade = gradeMapper.findByStudentIdAndCourseId(studentWithGrade.getId(),(long)1);
        grade.setPoint(studentWithGrade.getPoint());
        gradeMapper.update(grade);
    }

    @Test
    void manager(){
        //修改学生信息
        /*Student student = studentMapper.findById((long)2511);
        student.setName("小李");
        student.setSex("女");
        studentMapper.update(student);
        System.out.println(studentMapper.findById(student.getId()));*/


        //修改教师信息
        /*Teacher teacher = teacherMapper.findById((long)1223);
        teacher.setSex("女");
        teacherMapper.update(teacher);
        System.out.println(teacherMapper.findById(teacher.getId()));*/

        //教师列表
        //System.out.println(teacherMapper.findAll());

        //删除教师信息
        /*Long teacherId = (long) 1223;
        userMapper.deleteByUsername(teacherId.toString());
        List<Course> courseList = courseMapper.findByTeacherId(teacherId);
        for(Course course : courseList) {
            gradeMapper.deleteByCourseId(course.getId());
            courseMapper.deleteById(course.getId());
        }
        teacherMapper.deleteById(teacherId);*/

        //删除学生信息
        /*Long studentId = (long) 2521;
        userMapper.deleteByUsername(studentId.toString());
        gradeMapper.deleteByStudentId(studentId);
        studentMapper.deleteById(studentId);*/

        //添加课程
        /*Course course = courseMapper.findById((long)3);
        course.setId((long)10);
        course.setName("体育");
        course.setTeacherId((long)1230);
        System.out.println(courseMapper.create(course));*/
    }

    @Test
    void user(){
        //查找用户
        /*Long id = (long)2521;
        System.out.println(userMapper.findByUsername(id.toString()));*/

        //修改密码
        String username = "1223";
        User user = userMapper.findByUsername(username);
        user.setPassword("996");
        userMapper.update(user);
        System.out.println(userMapper.findByUsername(username));
    }
}
