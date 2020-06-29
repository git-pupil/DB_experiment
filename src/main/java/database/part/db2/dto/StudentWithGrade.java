package database.part.db2.dto;

import lombok.Data;

//老师需要
@Data
public class StudentWithGrade {
    private Long id;
    private String name;
    private int point;
}
