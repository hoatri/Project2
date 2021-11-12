/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import javafx.collections.ObservableList;

/**
 *
 * @author Admin
 */
public interface query<T> {

    public ObservableList<T> selectAll();

    public T insert(T t);

    public boolean update(T t);

    public boolean delete(T t);

    public T select(Integer i);
}
