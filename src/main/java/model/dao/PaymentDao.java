package model.dao;

import model.entity.Payment;

import javax.naming.NamingException;
import java.sql.SQLException;

public interface PaymentDao extends CrudDao<Long, Payment> {
    Long getIdOfPayment(Payment payment) throws NamingException, SQLException;
}
