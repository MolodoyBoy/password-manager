package com.password.manager.integration.db;

import com.password.manager.password.PasswordBundle;
import com.password.manager.password.PasswordSource;
import com.password.manager.user.User;
import com.password.manager.user.UserSession;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static java.util.function.Function.identity;

@Repository
class DbPasswordSource implements PasswordSource {

    private final UserSession userSession;
    private final JdbcTemplate jdbcTemplate;

    public DbPasswordSource(UserSession userSession, JdbcTemplate jdbcTemplate) {
        this.userSession = userSession;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String getBundlePassword(String bundle) {
        User user = userSession.getSessionUser();
        return jdbcTemplate.queryForObject(
                "SELECT password FROM user_password WHERE username = ? and bundle = ?;",
                new Object[]{bundle, user.username()},
                String.class
        );
    }

    @Override
    public Map<String, PasswordBundle> getUserPasswords() {
        User user = userSession.getSessionUser();
        return jdbcTemplate.queryForList(
                        "SELECT * FROM user_password where username = ?;",
                        PasswordBundle.class,
                        new Object[]{user.username()}
                ).stream()
                .collect(toMap(PasswordBundle::password, identity()));
    }

    @Override
    public boolean createPassword(PasswordBundle passwordBundle) {
        User user = userSession.getSessionUser();
        int rows = jdbcTemplate.update("INSERT INTO user_password VALUES (?, ?, ?) ON CONFLICT DO NOTHING;",
                user.username(),
                passwordBundle.bundle(),
                passwordBundle.password()
        );

        return rows > 0;
    }

    @Override
    public boolean updatePassword(PasswordBundle passwordBundle) {
        User user = userSession.getSessionUser();

        int rows = jdbcTemplate.update("UPDATE user_password VALUES SET password = ? WHERE username = ? AND bundle = ?;",
                passwordBundle.password(),
                user.username(),
                passwordBundle.bundle()
        );

        return rows > 0;
    }
}