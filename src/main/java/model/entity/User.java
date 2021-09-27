package model.entity;

import model.builder.UserBuilder;

import java.util.Objects;

public class User extends Model {
    private String name;
    private String email;
    private String password;
    private String role;
    private String activityStatus;

    private User(UserBuilderImpl builder) {
        super(builder.id);
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.role = builder.role;
        this.activityStatus = builder.activityStatus;
    }

    public static class UserBuilderImpl implements UserBuilder {

        private long id;
        private String name;
        private String email;
        private String password;
        private String role;
        private String activityStatus;

        @Override
        public UserBuilder setId(long id) {
            this.id = id;
            return this;
        }

        @Override
        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        @Override
        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        @Override
        public UserBuilder setRole(String role) {
            this.role = role;
            return this;
        }

        @Override
        public UserBuilder setActivityStatus(String activityStatus) {
            this.activityStatus = activityStatus;
            return this;
        }

        @Override
        public User build() {
            return new User(this);
        }
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(email, user.email)
                && Objects.equals(password, user.password) && Objects.equals(role, user.role)
                && Objects.equals(activityStatus, user.activityStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password, role, activityStatus);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", activityStatus='" + activityStatus + '\'' +
                '}';
    }
}
