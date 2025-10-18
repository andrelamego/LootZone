package lamego.lootzone.infrastructure.repositories;

import lamego.lootzone.domain.entities.MetodoPgto;
import lamego.lootzone.infrastructure.database.IDBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MetodoPgtoRepository {

    Connection c;
    IDBConnection dbconnection;

    public MetodoPgtoRepository (IDBConnection dbconnection) throws SQLException, ClassNotFoundException {
        this.dbconnection = dbconnection;
        c = dbconnection.getConnection();
    }

    public MetodoPgto buscar(long id) throws SQLException {
        String sql = "SELECT * FROM t_metodosPgto WHERE Id = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setLong(1, id);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            MetodoPgto metodo = new MetodoPgto();
            metodo.setId(id);
            metodo.setMetodo(rs.getString("Nome"));
            metodo.setJuros(rs.getFloat("Juros"));

            return metodo;
        }

        return null;
    }
}
