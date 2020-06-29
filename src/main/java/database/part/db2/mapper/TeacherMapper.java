package database.part.db2.mapper;

import database.part.db2.entity.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherMapper {

    public List<Teacher> findByCollege(String college);

    public List<Teacher> findAll();
    public Teacher findById(@Param(value = "id")Long id);
    public int create(Teacher teacher);
    public int update(Teacher teacher);

    public int deleteById(@Param(value = "id")Long id);
}
