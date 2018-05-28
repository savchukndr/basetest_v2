import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrii Savchuk on 27.05.2018.
 * All rights are reserved.
 * If you will have any cuastion, please
 * contact via email (savchukndr@gmail.com)
 */
public class Postgres {
    private Connection conn;
    private Statement stmt;
    private Connector connect;

    public Postgres(){
        connect = new Connector();
        connect.connect();
        conn = connect.getConn();
    }

    public Connection getConn() {
        return conn;
    }

    public void query(String createSql){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(createSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet selectAllquery(String selectQuery){
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(selectQuery);
        } catch (SQLException ignored) {
        }
        return resultSet;
    }
}