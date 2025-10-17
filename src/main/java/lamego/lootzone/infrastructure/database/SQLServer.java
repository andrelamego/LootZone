package lamego.lootzone.infrastructure.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServer implements IDBConnection{

    private Connection c;

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        String hostName = "localhost";
        String dbName = "lootzoneDB";
        String user = "andre";
        String password = "100306";

        Class.forName("net.sourceforge.jtds.jdbc.Driver");

        c = DriverManager.getConnection(String.format(
                "jdbc:jtds:sqlserver://%s:1433;databaseName=%s;user=%s;password=%s", hostName, dbName, user, password
        ));

        return c;
    }
}
