package database.part.db2.entity;

import lombok.Data;

@Data
public class Teacher {
    private Long id;
    private String name;
    private String sex;
    private String email;
    private String college;
}
