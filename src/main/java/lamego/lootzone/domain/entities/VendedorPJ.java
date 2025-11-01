package lamego.lootzone.domain.entities;

public class VendedorPJ extends Vendedor{
    private String cnpj;

    public VendedorPJ() {
        super();
    }

    public VendedorPJ(Usuario usuario) {
        this.setNome(usuario.getNome());
        this.setSobrenome(usuario.getSobrenome());
        this.setEmail(usuario.getEmail());
        this.setSenha(usuario.getSenha());
        this.setTelefone(usuario.getTelefone());
        this.setDataNascimento(usuario.getDataNascimento());
    }

    //GETTERS N SETTERS
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
