package lamego.lootzone.domain.entities;

import java.util.LinkedList;
import java.util.List;

public class Carrinho {
    private List<Produto> produtos = new LinkedList<>();

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public void removerProduto(Produto produto) {
        produtos.removeIf(p -> p == produto);
    }

    public float calcularTotal() {
        float total = 0;

        for (Produto p : produtos){
            total += p.getValor();
        }

        return total;
    }

    public void esvaziar(){
        produtos.clear();
    }
}
