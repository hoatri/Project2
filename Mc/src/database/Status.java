/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Valan
 */
public class Status {
    private ObjectProperty<Integer> statusID;
    private StringProperty statusName;

    public Status() {
        this.statusID = new SimpleObjectProperty<>();
        this.statusName = new SimpleStringProperty();

    }

    public Integer getStatusID() {
        return statusID.get();
    }

    public void setStatusID(Integer statusID) {
        this.statusID.set(statusID);
    }

    public String getStatusName() {
        return statusName.get();
    }

    public void setStatusName(String statusName) {
        this.statusName.set(statusName);
    }

    public ObjectProperty<Integer> getStatusIDProperty() {
        return statusID;
    }

    public StringProperty getStatusNameProperty() {
        return statusName;
    }

    @Override
    public String toString(){
        return statusName.get();
    }
}
