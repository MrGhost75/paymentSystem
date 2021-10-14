package model.dao.constants;

public class LogInfo {


    public static final String ADD = " method add()";
    public static final String GET_BY_ID = "method getById() with id=";
    public static final String GET_USER_BY_NAME = "method getUserByName() with name=";
    public static final String GET_USER_BY_EMAIL = "method getUserByEmail() with email=";
    public static final String GET_ALL_USER_CARDS= "method getAllUserCards() with user's id=";
    public static final String GET_ALL = "method getAll() ";
    public static final String UPDATE = " method update() ";
    public static final String DELETE = " method delete() with id=";
    public static final String STARTED = " has started.";
    public static final String SUCCESS = " successfully completed.";
    public static final String FAILED = " has failed. Cause:";
    public static final String ROLLBACK = " has failed. Connection rollback.";
    public static final String CLOSE_CONNECTION = "Close connection.";

    private LogInfo() {
    }
}
