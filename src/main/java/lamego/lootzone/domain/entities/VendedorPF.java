package lamego.lootzone.domain.entities;

public class VendedorPF extends Vendedor{
    private String cpf;
    private String comprovante;

    public VendedorPF() {
        super();
    }

    public VendedorPF(Usuario usuario) {
        this.setNome(usuario.getNome());
        this.setSobrenome(usuario.getSobrenome());
        this.setEmail(usuario.getEmail());
        this.setSenha(usuario.getSenha());
        this.setTelefone(usuario.getTelefone());
        this.setDataNascimento(usuario.getDataNascimento());
    }

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
