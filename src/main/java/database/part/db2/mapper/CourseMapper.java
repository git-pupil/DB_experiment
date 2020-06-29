package database.part.db2.mapper;

import database.part.db2.dto.CourseInfo;
import database.part.db2.dto.GradeWithCourse;
import database.part.db2.dto.StudentWithGrade;
import database.part.db2.entity.Course;
import database.part.db2.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMapper {

    public List<CourseInfo> findInfo();
    public List<CourseInfo> findInfoByCollege(String college);


    public List<Course> findAll();
    public List<Course> findByTeacherId(@Param(value = "id")Long teacherId);
    public Course findById(@Param(value = "id")Long id);
    public int create(Course course);
    public int update(Course course);
    public int deleteById(@Param(value = "id")Long id);

    public List<GradeWithCourse> findGradeById(@Param(value = "id")Long studentId);
}
