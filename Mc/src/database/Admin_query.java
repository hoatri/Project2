/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Valan
 */
public class Admin_query implements query<Admin>{

    @Override
    public ObservableList<Admin> selectAll() {
        ObservableList<Admin> admins = FXCollections.observableArrayList();

        try (
            Connection conn = Database.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Admin");) {
            while (rs.next()) {
                Admin a = new Admin();
                a.setAdminId(rs.getInt("adminID"));
                a.setAdminName(rs.getString("adminName"));
                a.setEmail(rs.getString("email"));
                a.setPassword(rs.getString("password_hash"));
                a.setPhone(rs.getString("phone"));
                admins.add(a);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return admins;
    }

    @Override
    public Admin insert(Admin newAdmin) {
        String sql = "INSERT into admin (adminName,email,password_hash,phone) "
                + "VALUES (?, ?, ?, ?)";
        ResultSet key = null;
        try (
            Connection conn = Database.getConnection();
            PreparedStatement stmt= conn.prepareStatement(sql);) {
            stmt.setString(1, newAdmin.getAdminName());
            stmt.setString(2, newAdmin.getEmail());
            stmt.setString(3, newAdmin.getPassword());
            stmt.setString(4, newAdmin.getPhone());

            int rowInserted = stmt.executeUpdate();

            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                newAdmin.setAdminId(newKey);
                return newAdmin;
            } else {
                System.err.println("No admin inserted");
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
    public boolean update(Admin admin) {
        String sql = "UPDATE admin SET `adminName` = ?, email = ?, password_hash = ?, phone= ? WHERE adminID = ?";
        try (
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, admin.getAdminName());
            stmt.setString(2, admin.getEmail());
            stmt.setString(3, admin.getPassword());
            stmt.setString(4, admin.getPhone());
            stmt.setInt(5, admin.getAdminId());

            int updated_num_rows = stmt.executeUpdate();
            if (updated_num_rows == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(Admin admin) {
        String sql = "DELETE FROM admin WHERE adminID = ?;";
        try (
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, admin.getAdminId());
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
    public Admin select(Integer id) {
        String sql = "SELECT * FROM admin WHERE adminID = ?";
        ResultSet rs = null;
        try (
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Admin a = new Admin();
                a.setAdminId(rs.getInt("adminID"));
                a.setAdminName(rs.getString("adminName"));
                a.setEmail(rs.getString("email"));
                a.setPhone(rs.getString("phone"));
                a.setPassword(rs.getString("password_hash"));
                return a;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        return null;
    }
    
    public Admin select(String email) {
        String sql = "SELECT * FROM admin WHERE email = ?";
        ResultSet rs = null;
        try (
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Admin a = new Admin();
                a.setAdminId(rs.getInt("adminID"));
                a.setAdminName(rs.getString("adminName"));
                a.setEmail(rs.getString("email"));
                a.setPhone(rs.getString("phone"));
                a.setPassword(rs.getString("password_hash"));
                return a;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        return null;
    }
    
    public static boolean authenticateAdmin(String email, String password) {
        String sql = "SELECT * FROM Admin WHERE email = ? AND password_hash = ?";
        try (
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
    
    public boolean isUnique(String email){
        String sql = "SELECT * FROM admin WHERE email = ?";
        try (
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return true;
    }
}
