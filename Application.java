import java.sql.*;


public class Application {
    public static void main(String[] args) {
       
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            ConnectionPool.getConnection("jdbc:mysql://ubuntu:3306/wpblog", "root", "q1w2e3r4");

            Command c = null;
           
            c = new Delete();

            c = new Insert();

            c = new Select();

            ConnectionPool.close();
           
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

interface Printable {
}

abstract class Command {
    protected Connection conn;

    public Command() {
        try {
            conn = ConnectionPool.getInstance();
           
            if (this instanceof Printable) {
                print(execute());
            } else {
                execute();
            }
           
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public abstract ResultSet execute();

    public void print(ResultSet rs) {
        try {
            while (rs.next()) {
                String s = "equip_id=" + rs.getString("equip_id") + ",type  ="
                        + rs.getString("type");
                System.out.println(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class Update extends Command {


    public ResultSet execute() {
        try {
            String sql = "UPDATE equipment SET type= ? WHERE equip_id= ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "2");
            ps.setString(2, "2");
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }
}

class Delete extends Command {


    public ResultSet execute() {
        try {
            String sql = "DELETE FROM equipment";// where equip_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            // ps.setString(1, "1");
            ps.executeQuery();

            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }
}

class Insert extends Command {

    public ResultSet execute() {
        try {
            String sql = "INSERT INTO equipment (type , color, working, location) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "Slide");
            ps.setString(2, "blue");
            ps.setString(3, "1");
            ps.setString(4, "1");
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }
}

class Select extends Command implements Printable {


    public ResultSet execute() {
        try {
            String sql = "SELECT * FROM  equipment WHERE type= ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "Slide");
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }
}

class ConnectionPool {
    private static Connection conn;

    public static Connection getInstance() throws SQLException  {
        if (conn == null) {
            throw new SQLException();
        } else {
            return conn;
        }
    }

    private ConnectionPool() {

    }

    public static void getConnection(String url, String user, String password)
            throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection(url, user, password);
        }
    }

    public static void close()throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
