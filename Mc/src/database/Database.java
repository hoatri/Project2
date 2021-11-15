/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Valan
 */
public class Database {
    private static final String MYSQL_USERNAME = "root";
    private static final String MYSQL_PASSWORD = "";
    private static final String MYSQL_CONN_STRING
        = "jdbc:mysql://localhost/breakfast?serverTimezone=UTC";

    private static final String URL
        ="jdbc:sqlserver://localhost\\SQLEXPRESS;DatabaseName=breakfast";
    private static final String USERNAME="sa";
    private static final String PASSWORD="12";
    private static Connection connection=null;
    
    public static Connection getConnection() throws ClassNotFoundException{
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Database.connection=DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if(connection!=null){
                DatabaseMetaData data=(DatabaseMetaData)Database.connection.getMetaData();
            }else{
                Database.connection=DriverManager.getConnection(MYSQL_CONN_STRING,MYSQL_USERNAME,MYSQL_PASSWORD);
            }
            return Database.connection;
        }catch(SQLException ex){
            return null;
        }
    }
    
//    public static Connection getConnection(String i) throws ClassNotFoundException{
//        try{
//            
//            if(i=="server"){
//                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//                Database.connection=DriverManager.getConnection(URL, USERNAME, PASSWORD);
//                if(connection!=null){
//                    DatabaseMetaData data=(DatabaseMetaData)Database.connection.getMetaData();
//                }else{
//                    return null;
//                }
//            }else if(i=="heidi"){
//                Database.connection=DriverManager.getConnection(MYSQL_CONN_STRING,MYSQL_USERNAME,MYSQL_PASSWORD);           
//                if(connection==null){
//                    return null;
//                }
//            }
//            return Database.connection;
//        }catch(SQLException ex){
//            return null;
//        }
//    }
}
