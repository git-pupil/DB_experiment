package database.part.db2.mapper.auth;

import database.part.db2.entity.auth.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    public User findByUsername(@Param(value = "username") String username);
    public int deleteByUsername(@Param(value = "username") String username);

    public int update(User user);
}
