package database.part.db2.mapper;

import database.part.db2.entity.Grade;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeMapper {
    public Grade findByStudentIdAndCourseId(@Param(value = "sId")Long studentId, @Param(value = "cId")Long courseId);
    public int update(Grade grade);
    public int create(Grade grade);
    public int deleteById(@Param(value = "id")Long id);
    public int deleteByStudentId(@Param(value = "id")Long studentId);
    public int deleteByCourseId(@Param(value = "id")Long courseId);

    public int countStage(@Param(value = "id")Long courseId,int low,int high);
    public int count ();
}
