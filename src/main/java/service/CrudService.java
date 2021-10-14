package service;

import model.entity.Model;
import model.exception.DataBaseException;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public interface CrudService <L, E extends Model> {

    boolean add(E entity) throws NamingException;

    E getById(L id) throws NamingException, DataBaseException;

    List<E> getAll() throws NamingException, DataBaseException;

    boolean updateEntity(E entity) throws NamingException;

    boolean deleteEntity(L id) throws NamingException;

}