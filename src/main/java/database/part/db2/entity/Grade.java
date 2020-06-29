package database.part.db2.entity;

import lombok.Data;

@Data
public class Grade {
    private Long id;
    private int point;
    private Long studentId;
    private Long courseId;

    public Grade(){};
    public Grade(Long studentId, Long courseId){
        this.point = 0;
        this.studentId = studentId;
        this.courseId = courseId;
    }
}
