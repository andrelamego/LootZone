package lamego.lootzone.application.interfaces;

import lamego.lootzone.domain.entities.Item;
import lamego.lootzone.domain.entities.Jogo;

import java.sql.SQLException;

public interface IProdutoRepository<T> extends IRepository<T>{
    void venderItem(T entidade, int qnt) throws SQLException;
    Jogo buscar(Jogo jogo) throws SQLException, ClassNotFoundException;
    Item buscar(Item item) throws SQLException, ClassNotFoundException;
}
