/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author dmitjans
 */
public class DbConnection {
    
    private Connection connection;
    private String url = "lib/p0BBDD.db";
    
    public Connection getConnection () throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:"+url);
        Helper.imprimirTraza("Conectado");
        return connection;
    }

}
    

