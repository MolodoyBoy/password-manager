package com.password.manager.integration.db;

import com.password.manager.password.PasswordBundle;
import com.password.manager.password.PasswordSource;
import com.password.manager.password.encryptor.PasswordEncryptor;
import com.password.manager.user.User;
import com.password.manager.user.UserSession;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static java.util.function.Function.identity;

@Repository
class DbPasswordSource implements PasswordSource {

    private final UserSession userSession;
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncryptor passwordEncryptor;

    DbPasswordSource(UserSession userSession,
                     JdbcTemplate jdbcTemplate,
                     PasswordEncryptor passwordEncryptor) {
        this.userSession = userSession;
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncryptor = passwordEncryptor;
    }

    @Override
    public String getBundlePassword(String bundle) {
        User user = userSession.getSessionUser();

        RowMapper<String> rowMapper = (rs, rowNum) -> rs.getString("password");

        String password = jdbcTemplate.queryForStream(
                "SELECT password FROM user_password WHERE username = ? and bundle = ?;",
                rowMapper,
                user.username(), bundle
        ).findFirst().orElse(null);

        return passwordEncryptor.decrypt(password);
    }

    @Override
    public Map<String, PasswordBundle> getUserPasswords() {
        User user = userSession.getSessionUser();

        RowMapper<PasswordBundle> rowMapper = (rs, rowNum) ->
                new PasswordBundle(rs.getString("bundle"), rs.getString("password"));

        return jdbcTemplate.queryForStream(
                        "SELECT bundle, password FROM user_password where username = ?;",
                        rowMapper,
                        user.username()
                ).map(this::getPasswordBundle)
                .collect(toMap(PasswordBundle::password, identity()));
    }

    private PasswordBundle getPasswordBundle(PasswordBundle dbPasswordBundle) {
        return new PasswordBundle(
                dbPasswordBundle.bundle(),
                passwordEncryptor.decrypt(dbPasswordBundle.password())
        );
    }

    @Override
    public boolean createPassword(PasswordBundle passwordBundle) {
        User user = userSession.getSessionUser();
        int rows = jdbcTemplate.update("INSERT INTO user_password VALUES (?, ?, ?) ON CONFLICT DO NOTHING;",
                user.username(),
                passwordBundle.bundle(),
                passwordEncryptor.encrypt(passwordBundle.password())
        );

        return rows > 0;
    }

    @Override
    public boolean updatePassword(PasswordBundle passwordBundle) {
        User user = userSession.getSessionUser();

        int rows = jdbcTemplate.update("UPDATE user_password VALUES SET password = ? WHERE username = ? AND bundle = ?;",
                passwordEncryptor.encrypt(passwordBundle.password()),
                user.username(),
                passwordBundle.bundle()
        );

        return rows > 0;
    }
}