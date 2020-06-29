package database.part.db2.mapper;


import database.part.db2.dto.StudentInfo;
import database.part.db2.dto.StudentWithGrade;
import database.part.db2.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMapper {

    public List<StudentInfo> findInfo();
    public List<StudentInfo> findInfoByCollege(String college);

    public List<StudentInfo> findAllInfo();
    public List<Student> findAll();
    public Student findById(@Param(value = "id")Long id);
    public StudentInfo findInfoById(@Param(value = "id")Long id);
    public int create(Student student);
    public int update(Student student);
    public int deleteById(@Param(value = "id")Long id);

    public List<StudentWithGrade> findGradeById(@Param(value = "id")Long courseId);
}
