package com.password.manager.integration.db;

import com.password.manager.user.User;
import com.password.manager.user.UserSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
class DbUserSource implements UserSource {

    private final JdbcTemplate jdbcTemplate;

    public DbUserSource(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean createUser(User user) {
        int rows = jdbcTemplate.update(
                "INSERT INTO user_info values (?, ?);", user.username(), user.password()
        );

        return rows > 0;
    }

    @Override
    public boolean existUser(User user) {
        Integer rows = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM user_info WHERE username = ? and password = ?;",
                new Object[]{user.username(), user.password()},
                Integer.class
        );

        return rows > 0;
    }
}