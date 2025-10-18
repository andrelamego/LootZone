package lamego.lootzone.application.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IRepository<T> {
    void salvar(T entidade) throws SQLException;
    void atualizar(T entidade) throws SQLException;
    void excluir(T entidade) throws SQLException;
    T buscar(T entidade) throws SQLException, ClassNotFoundException;
    List<T> listar() throws SQLException, ClassNotFoundException;
}
