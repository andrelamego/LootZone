package lamego.lootzone.infrastructure.repositories;

import lamego.lootzone.application.interfaces.IRepository;
import lamego.lootzone.domain.entities.Usuario;
import lamego.lootzone.infrastructure.database.IDBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsuarioRepository implements IRepository<Usuario> {

    private Connection c;
    private IDBConnection dbConnection;

    public UsuarioRepository(IDBConnection dbConnection) throws SQLException, ClassNotFoundException {
        this.dbConnection = dbConnection;
        c = dbConnection.getConnection();
    }

    @Override
    public void salvar(Usuario entidade) throws SQLException{

    }

    @Override
    public void atualizar(Usuario entidade) {

    }

    @Override
    public void excluir(Usuario entidade) {

    }

    @Override
    public Usuario buscar(int id) {
        return null;
    }

    @Override
    public List<Usuario> listar() {
        return List.of();
    }
}
