/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Valan
 */
public class Product_query implements query<Product>{

    @Override
    public ObservableList<Product> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product insert(Product t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Product t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Product t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product select(Integer i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ObservableList<Product> selectAllActive() {
        ObservableList<Product> products = FXCollections.observableArrayList();

        try (
                Connection conn = Database.getConnection();
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT product.*, category.categoryName FROM product LEFT JOIN category ON product.categoryID = category.categoryID WHERE `status` <> 5 OR `status` IS NULL");) {
                while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("productID"));
                p.setProductName(rs.getString("productName"));
                p.setPrice(rs.getDouble("price"));
                p.setStock(rs.getInt("stock"));
                p.setDescription(rs.getString("description"));
                p.setImage(rs.getString("image"));
                p.setCategoryId(rs.getInt("categoryID"));
                p.setCategoryName(rs.getString("categoryName"));
                products.add(p);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return products;
    }
    
}
