package lamego.lootzone.infrastructure.repositories;

import lamego.lootzone.application.interfaces.IRepository;
import lamego.lootzone.domain.entities.Produto;

import java.util.List;

public class ProdutoRepository implements IRepository<Produto> {
    @Override
    public void salvar(Produto entidade) {

    }

    @Override
    public void atualizar(Produto entidade) {

    }

    @Override
    public void excluir(Produto entidade) {

    }

    @Override
    public Produto buscar(int id) {
        return null;
    }

    @Override
    public List<Produto> listar() {
        return List.of();
    }
}
