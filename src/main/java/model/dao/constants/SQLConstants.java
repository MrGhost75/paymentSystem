package model.dao.constants;

public enum SQLConstants {

    //t_user CRUD-queries
    INSERT_USER("INSERT INTO t_user(name, email, password, role, status) VALUES(?, ?, ?, ?, ?)"),
    GET_USER_BY_ID("SELECT * FROM t_user WHERE ID = ?"),
    GET_ALL_USERS("SELECT * FROM t_user"),
    UPDATE_USER("UPDATE t_user SET name = ?, email = ?, password = ?, role = ?, status = ? WHERE ID = ?"),
    DELETE_USER_BY_ID("DELETE FROM t_user WHERE ID = ?"),

    //t_credit_card CRUD-queries
    INSERT_CREDIT_CARD("INSERT INTO t_credit_card(name, pincode, balance, status) VALUES(?, ?, ?, ?)"),
    GET_CREDIT_CARD_BY_ID("SELECT * FROM t_credit_card WHERE ID = ?"),
    GET_ALL_CARDS("SELECT * FROM t_credit_card"),
    UPDATE_CREDIT_CARD("UPDATE t_credit_card SET name = ?, pincode = ?, balance = ?, status = ? WHERE ID = ?"),
    DELETE_CREDIT_CARD_BY_ID("DELETE FROM t_credit_card WHERE ID = ?"),

    //t_payment CRUD-queries
    INSERT_PAYMENT("INSERT INTO t_payment(amount, date, description, status) VALUES(?, ?, ?, ?)"),
    GET_PAYMENT_BY_ID("SELECT * FROM t_payment WHERE ID = ?"),
    GET_ALL_PAYMENTS("SELECT * FROM t_payment"),
    UPDATE_PAYMENT("UPDATE t_payment SET amount = ?, date = ?, description = ?, status = ? WHERE ID = ?"),
    DELETE_PAYMENT_BY_ID("DELETE FROM t_payment WHERE ID = ?"),

    //t_user queries
    SELECT_USER_BY_NAME("SELECT * FROM t_user WHERE name = ?"),
    SELECT_USER_BY_EMAIL("SELECT * FROM t_user WHERE email = ?"),

    //t_user_cards queries
    SELECT_ALL_USER_CARDS("SELECT id, name, pincode, balance, status FROM t_credit_card " +
                          "JOIN t_user_cards tuc on t_credit_card.id = tuc.card_id " +
                                  "WHERE user_id = ?");

    private final String constant;

    public String getConstant() {
        return constant;
    }

    SQLConstants(String constant) {
        this.constant = constant;
    }

}
