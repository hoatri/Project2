package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;

public class Customer_query implements query<Customer>{
    @Override
    public ObservableList<Customer> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static boolean isUnique(String email){
        String sql = "SELECT * FROM Customer WHERE email = ?";
        try (
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (Exception e) {
        }
        return true;
    }
    
    @Override
    public Customer insert(Customer newCustomer) {
        String sql = "INSERT INTO customer (fullName, email, password_hash, address,  phone) "
                + "VALUES (?, ?, ?, ?, ?)";
        ResultSet key = null;
        try (
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);){
            stmt.setString(1, newCustomer.getFullName());
            stmt.setString(2, newCustomer.getEmail());
            stmt.setString(3, newCustomer.getPassword());
            stmt.setString(4, newCustomer.getAddress());
            stmt.setString(5, newCustomer.getPhone());

            int rowInserted = stmt.executeUpdate();

            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                newCustomer.setCustomerID(newKey);
                return newCustomer;
            } else {
                System.err.println("No customer inserted");
                return null;
            }

        } catch (Exception e) {
            System.err.println(e);
            return null;
        } finally {
            try {
                if (key != null) {
                    key.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        }
    }

    @Override
    public boolean update(Customer t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Customer t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Customer select(Integer i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
