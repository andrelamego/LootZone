package lamego.lootzone.domain.entities;

import java.time.LocalDateTime;

public class Pagamento {
    private long id;
    private float valor;
    private LocalDateTime data;
    private String status;
    private MetodoPgto metodo;

    public void processar(){

    }

    public void estornar(){

    }

    //GETTERS N SETTERS
    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MetodoPgto getMetodo() {
        return metodo;
    }

    public void setMetodo(MetodoPgto metodo) {
        this.metodo = metodo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
