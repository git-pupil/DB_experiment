package database.part.db2.mapper;

import database.part.db2.entity.Manager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerMapper {
    public Manager findById(@Param(value = "id") Long id);
}
