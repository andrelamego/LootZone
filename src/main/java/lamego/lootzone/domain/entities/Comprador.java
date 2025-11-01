package lamego.lootzone.domain.entities;

public class Comprador extends Usuario{
    private float credito;

    public Comprador() {
        super();
    }

    public Comprador(Usuario usuario) {
        this.setNome(usuario.getNome());
        this.setSobrenome(usuario.getSobrenome());
        this.setEmail(usuario.getEmail());
        this.setSenha(usuario.getSenha());
        this.setTelefone(usuario.getTelefone());
        this.setDataNascimento(usuario.getDataNascimento());
    }

    //GETTERS N SETTERS
    public float getCredito() {
        return credito;
    }

    public void setCredito(float credito) {
        this.credito = credito;
    }
}
