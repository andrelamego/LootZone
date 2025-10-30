package lamego.lootzone.domain.entities;

import java.sql.Date;
import java.time.LocalDate;

public class Usuario {
    private long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private String telefone;
    private LocalDate dataNascimento;

    public Usuario(){
        super();
    }

    public Usuario(String nome, String sobrenome, String email, String senha, String telefone, LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
    }



    //GETTERS N SETTERS
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
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
