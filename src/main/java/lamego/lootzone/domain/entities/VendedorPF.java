package lamego.lootzone.domain.entities;

public class VendedorPF extends Vendedor{
    private String cpf;
    private String comprovante;

    //GETTERS N SETTERS
    public String getComprovante() {
        return comprovante;
    }

    public void setComprovante(String comprovante) {
        this.comprovante = comprovante;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
