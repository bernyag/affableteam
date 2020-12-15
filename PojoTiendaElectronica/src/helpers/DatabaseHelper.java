/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

/**
 *
 * @author jaimelimonsamperio
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseHelper {
    private Connection conn;
    private String[] tables = {"books", "client", "order_book", "delivery_company", "delivery_order"};
    private String[] createQueries = {
        "CREATE TABLE books(" +
            " isbn INT NOT NULL GENERATED ALWAYS AS IDENTITY" +
            " CONSTRAINT BOOKS_PK PRIMARY KEY, " +
            " unitsAvailable INT NOT NULL, " +
            " unitsOnHold INT NOT NULL, " +
            " price DECIMAL(6,2) NOT NULL)",
        "CREATE TABLE client(" +
            " clientId INT NOT NULL GENERATED ALWAYS AS IDENTITY" +
            " (START WITH 1,INCREMENT BY 1)" +
            " CONSTRAINT CLIENT_PK PRIMARY KEY," +
            " balance DECIMAL(6,2) NOT NULL)",
        "CREATE TABLE order_book(" +
            " orderId INT NOT NULL GENERATED ALWAYS AS IDENTITY" +
            " (START WITH 1, INCREMENT BY 1)" +
            " CONSTRAINT ORDER_PK PRIMARY KEY," +
            " unitsOrdered INT NOT NULL," +
            " finalCost DECIMAL(6,2) NOT NULL," +
            " isbn int NOT NULL" +
            " CONSTRAINT ISBN_FK REFERENCES books," +
            " clientId INT NOT NULL " +
            " CONSTRAINT CLIENT_ID_FK REFERENCES client)",
        "CREATE TABLE delivery_company(" +
            " idDelivery INT NOT NULL GENERATED ALWAYS AS IDENTITY" +
            " (START WITH 1, INCREMENT BY 1)" +
            " CONSTRAINT IDDELIVERY_PK PRIMARY KEY," +
            " name VARCHAR(30) NOT NULL)",
        "CREATE TABLE delivery_order(" +
            " deliverOrderId INT NOT NULL GENERATED ALWAYS AS IDENTITY" +
            " (START WITH 1, INCREMENT BY 1)" +
            " CONSTRAINT DELIVERY_ORDER_ID PRIMARY KEY," +
            " orderId INT NOT NULL" +
            " CONSTRAINT ORDER_ID_FK REFERENCES order_book," +
            " idDelivery INT NOT NULL" +
            " CONSTRAINT NAME_FK REFERENCES delivery_company," +
            " deliveryDays INT NOT NULL)"
    };

    public DatabaseHelper() throws ClassNotFoundException, SQLException {
        //Registering the driver
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        
        //Getting the Connection object
        String URL = "jdbc:derby://localhost:1527/affableteam;create=true";
        conn = DriverManager.getConnection(URL, "app", "app");
    }
    
    private void executeStatement(String query) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute(query);
    }
    
    public void givenEmptyDatabase() throws SQLException {
        String deleteQuery = "DELETE FROM ";
        for (String table : tables) {
            executeStatement(deleteQuery + table);
        }
    }
    
    private void createTables() {
        for (String createQuery : createQueries) {
            try {
                // TODO: execute statement only
                executeStatement(createQuery);
            } catch (SQLException ex) {
                int errorCode = ex.getErrorCode();
                if (errorCode != 30000) {
                    Logger.getLogger(DatabaseHelper.class.getName()).log(Level.INFO, null, ex);
                } else {
                    System.out.println("Table already exists");
                }
            }
        }
    }
    
    public static void main(String args[]) throws Exception {
        
        DatabaseHelper db = new DatabaseHelper();
        db.createTables();
        
   }
}