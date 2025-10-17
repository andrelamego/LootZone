package lamego.lootzone.domain.entities;

import java.sql.Date;
import java.time.LocalDate;

public abstract class Usuario {
    private long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private Date dataNascimento;

    //GETTERS N SETTERS
    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
