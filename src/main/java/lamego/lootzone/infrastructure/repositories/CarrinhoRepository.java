package lamego.lootzone.infrastructure.repositories;

import lamego.lootzone.application.interfaces.IRepository;
import lamego.lootzone.domain.entities.Carrinho;

import java.util.List;

public class CarrinhoRepository implements IRepository<Carrinho> {
    @Override
    public void salvar(Carrinho entidade) {

    }

    @Override
    public void atualizar(Carrinho entidade) {

    }

    @Override
    public void excluir(Carrinho entidade) {

    }

    @Override
    public Carrinho buscar(int id) {
        return null;
    }

    @Override
    public List<Carrinho> listar() {
        return List.of();
    }
}
