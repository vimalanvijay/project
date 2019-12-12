package database_conn;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class connectDatabase {

    //private String dbUrl = "jdbc:postgresql://ec2-46-137-113-157.eu-west-1.compute.amazonaws.com:5432/d14dthkltqk9a5?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory&user=vxclcusuaqsjhb&password=f612c5ffa8ab81bf83090168755548a8ea08e67ef3b863bd11505a20fdb6f0d3";
    private String dbUrl="jdbc:postgresql://localhost/project";

    private Connection conn = DriverManager.getConnection(dbUrl);

    public connectDatabase() throws SQLException {
        try {
            // Registers the driver
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public Connection getConnection() throws SQLException {
        return conn;
    }

    public Statement createStatement() throws SQLException {
       return conn.createStatement();
    }

    public void close() throws SQLException {
        conn.close();
    }
}
