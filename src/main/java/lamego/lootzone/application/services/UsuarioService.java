package lamego.lootzone.application.services;

import lamego.lootzone.domain.entities.Usuario;
import lamego.lootzone.infrastructure.repositories.UsuarioRepository;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public boolean cadastrarUsuario(Usuario user) throws SQLException {
        if(user == null) {
            throw new IllegalArgumentException("Usuario não pode ser nulo");
        }

        if (user.getNome() == null || user.getSobrenome() == null || user.getEmail() == null ||
            user.getTelefone() == null || user.getDataNascimento() == null) {

            throw new IllegalArgumentException("Todos os campos são obrigatorios");
        }

        usuarioRepository.salvar(user);
        return true;
    }

    public void atualizarUsuario(Usuario user) {}

    public void excluirUsuario(Usuario user) {}

    public void removerUsuario(Usuario user) {}

    public boolean buscarUsuario(Usuario user) throws SQLException {
        Usuario usuario = usuarioRepository.buscar(user);
        return usuario != null;
    }

    public List<Usuario> listarUsuarios() throws SQLException {
        return usuarioRepository.listar();
    }

    //REGRAS DE NEGOCIO ESPECIFICAS
    public boolean emailExists(String email) {
        return false;
    }

    public boolean telefoneExists(String telefone) {
        return false;
    }


    public boolean autenticar(String email, String password) throws SQLException {
        Usuario user = usuarioRepository.validateLogin(email);

        if (user == null)
            return false;

        if(user.getSenha().equals(password))
            return true;

        return false;
    }
}
