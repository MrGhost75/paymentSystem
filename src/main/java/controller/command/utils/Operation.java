package controller.command.utils;

public enum Operation {

    LOGIN("/view/login"),
    REGISTRATION("/view/registration"),
    LOGOUT("/view/logout"),

    ADMIN_MENU("/view/admin/mainPageAdmin"),
    ADMIN_PROFILE("/view/admin/profileAdmin"),

    CLIENT_MENU("/view/client/mainPageUser"),
    CLIENT_PROFILE("/view/client/profileUser"),

    PAYMENT_MAKING("/view/paymentMaking"),
    PAYMENT_HISTORY("/view/paymentHistory"),

    ADD_CREDIT_CARD("/view/addingCreditCard"),
    BALANCE_REPLENISHMENT("/view/balanceReplenishment");

    private final String command;

    public String getCommand() {
        return command;
    }

    Operation(String command) {
        this.command = command;
    }

}
