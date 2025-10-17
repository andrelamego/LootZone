package lamego.lootzone.infrastructure.repositories;

import lamego.lootzone.application.interfaces.IRepository;
import lamego.lootzone.domain.entities.Pedido;

import java.util.List;

public class PedidoRepository implements IRepository<Pedido> {
    @Override
    public void salvar(Pedido entidade) {

    }

    @Override
    public void atualizar(Pedido entidade) {

    }

    @Override
    public void excluir(Pedido entidade) {

    }

    @Override
    public Pedido buscar(int id) {
        return null;
    }

    @Override
    public List<Pedido> listar() {
        return List.of();
    }
}
