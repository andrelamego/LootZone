package lamego.lootzone.infrastructure.repositories;

import lamego.lootzone.application.interfaces.IRepository;
import lamego.lootzone.domain.entities.Pagamento;

import java.util.List;

public class PagamentoRepository implements IRepository<Pagamento> {
    @Override
    public void salvar(Pagamento entidade) {

    }

    @Override
    public void atualizar(Pagamento entidade) {

    }

    @Override
    public void excluir(Pagamento entidade) {

    }

    @Override
    public Pagamento buscar(int id) {
        return null;
    }

    @Override
    public List<Pagamento> listar() {
        return List.of();
    }
}
