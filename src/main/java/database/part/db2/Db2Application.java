package database.part.db2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "database.part.db2.mapper")
@MapperScan(value = "database.part.db2.mapper.auth")
public class Db2Application {

    public static void main(String[] args) {
        SpringApplication.run(Db2Application.class, args);
    }

}
