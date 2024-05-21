package com.password.manager.user;

public interface UserSource {

    boolean createUser(User user);

    boolean existUser(User user);
}