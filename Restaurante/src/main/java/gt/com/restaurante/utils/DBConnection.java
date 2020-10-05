package gt.com.restaurante.utils;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    private Connection con=null;

    public DBConnection() {

    }

    public Connection doConnection(){
        try{
            InitialContext ic=new InitialContext();
            DataSource ds=(DataSource)ic.lookup("jdbc/javaAntiguaBurguer");
            con = ds.getConnection();

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return con;
    }

    public Connection getConnection(){
        return con;
    }

    public void closeConnection(){
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
