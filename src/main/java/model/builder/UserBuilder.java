package model.builder;

import model.entity.User;

public interface UserBuilder {
    UserBuilder setId(long id);
    UserBuilder setName(String name);
    UserBuilder setEmail(String email);
    UserBuilder setPassword(String password);
    UserBuilder setRole(String role);
    UserBuilder setActivityStatus(String activityStatus);
    User build();
}
