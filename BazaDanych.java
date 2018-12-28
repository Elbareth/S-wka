package niemiecki;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lenovo
 */
public class BazaDanych {
    public Connection polacz(){
        Connection connect = null;      
        try{
            connect = DriverManager.getConnection("connection-string","login","haslo");            
        }
        catch(SQLException e){
            e.printStackTrace();
        }        
        return connect;
    }
}
