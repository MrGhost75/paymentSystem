package controller.command.utils;

public enum Operation {

    LOGIN("/view/login"),
    REGISTRATION("/view/registration"),
    LOGOUT("/view/logout"),

    ADMIN_MENU("/view/admin/mainPageAdmin"),

    CLIENT_MENU("/view/client/mainPageUser");

    private final String command;

    public String getCommand() {
        return command;
    }

    Operation(String command) {
        this.command = command;
    }

}
