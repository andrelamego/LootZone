package lamego.lootzone.domain.entities;

public class Item extends Produto{
    private int quantidade;

    //GETTERS N SETTERS
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
