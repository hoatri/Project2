package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer_query implements query<Customer>{
    @Override
    public ObservableList<Customer> selectAll() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        try (
                Connection conn = Database.getConnection();
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM customer");) {
            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerID(rs.getInt("customerID"));
                c.setFullName(rs.getString("fullName"));
                c.setEmail(rs.getString("email"));
                c.setPassword(rs.getString("password_hash"));
                c.setAddress(rs.getString("address"));
                c.setPhone(rs.getString("phone"));
                customers.add(c);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return customers;
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
    public boolean update(Customer customer) {
        String sql = "UPDATE customer SET fullName = ?, email = ?, address = ?, phone = ?, password_hash = ? WHERE customerID = ?";
        try (
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, customer.getFullName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getPhone());
            stmt.setString(5, customer.getPassword());
            stmt.setInt(6, customer.getCustomerID());

            int updated_num_rows = stmt.executeUpdate();
            if (updated_num_rows == 1) {
                return true;
            } else {
                System.err.println("Can't update");
                return false;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Customer customer) {
        String sql = "DELETE FROM customer WHERE customerID = ?;";
        try (
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setInt(1, customer.getCustomerID());
            int del_num_rows = stmt.executeUpdate();
            if (del_num_rows == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Customer select(Integer i) {
        String sql = "SELECT * FROM customer WHERE `customerID` = ?";
        ResultSet rs = null;
        try (
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, i);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Customer c = new Customer();
                c.setCustomerID(rs.getInt("customerID"));
                c.setFullName(rs.getString("fullName"));
                c.setEmail(rs.getString("email"));
                c.setAddress(rs.getString("address"));
                c.setPhone(rs.getString("phone"));
                return c;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        return null;
    }
    
    public Customer select(String email) {
        String sql = "SELECT * FROM customer WHERE `email` = ?";
        ResultSet rs = null;
        try (
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Customer c = new Customer();
                c.setCustomerID(rs.getInt("customerID"));
                c.setFullName(rs.getString("fullName"));
                c.setEmail(rs.getString("email"));
                c.setAddress(rs.getString("address"));
                c.setPhone(rs.getString("phone"));
                c.setPassword(rs.getString("password_hash"));
                return c;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        return null;
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
}
