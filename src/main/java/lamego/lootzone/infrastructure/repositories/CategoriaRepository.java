package lamego.lootzone.infrastructure.repositories;

import lamego.lootzone.application.interfaces.IRepository;
import lamego.lootzone.domain.entities.Categoria;
import lamego.lootzone.infrastructure.database.IDBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoriaRepository {

    Connection c;
    IDBConnection dbconnection;

    public CategoriaRepository(IDBConnection dbconnection) throws SQLException, ClassNotFoundException {
        this.dbconnection = dbconnection;
        c = dbconnection.getConnection();
    }

    public Categoria buscar(long id) throws SQLException {
        Categoria categoria = new Categoria();
        String sql = "SELECT Nome FROM t_categorias WHERE Id = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            categoria.setId(id);
            categoria.setNome(rs.getString(1));
        }

        return categoria;
    }
}
