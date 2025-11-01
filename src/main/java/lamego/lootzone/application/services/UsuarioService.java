package lamego.lootzone.application.services;

import lamego.lootzone.application.exceptions.RegraNegocioException;
import lamego.lootzone.domain.entities.Comprador;
import lamego.lootzone.domain.entities.Usuario;
import lamego.lootzone.domain.entities.VendedorPF;
import lamego.lootzone.domain.entities.VendedorPJ;
import lamego.lootzone.infrastructure.repositories.UsuarioRepository;
import lamego.lootzone.shared.utils.MaskUtils;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public void cadastrarComprador(Usuario user, String tfCredito) throws SQLException {
        String valor = MaskUtils.limparFormatacao(tfCredito);
        float credito = MaskUtils.parseValorMonetario(valor);

        if(credito < 10.0f)
            throw new RegraNegocioException("O valor mínimo é de R$10,00");

        Comprador comprador = new Comprador(user);
        comprador.setCredito(credito);

        usuarioRepository.salvar(comprador);
    }

    public void cadastrarVendedorPF(Usuario user, String tfCPF) throws SQLException {
        String cpf = MaskUtils.limparFormatacao(tfCPF);

        if(cpf.length() != 11)
            throw new RegraNegocioException("CPF inválido!");

        VendedorPF vendedorPF = new VendedorPF(user);
        vendedorPF.setCpf(cpf);

        usuarioRepository.salvar(vendedorPF);
    }

    public void cadastrarVendedorPJ(Usuario user, String tfCNPJ) throws SQLException {
        String cnpj = MaskUtils.limparFormatacao(tfCNPJ);

        if(cnpj.length() != 14)
            throw new RegraNegocioException("CNPJ inválido!");

        VendedorPJ vendedorPJ = new VendedorPJ(user);
        vendedorPJ.setCnpj(cnpj);

        usuarioRepository.salvar(vendedorPJ);
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
    public void emailExists(String email) throws SQLException {
        Usuario usuario = usuarioRepository.buscarPorEmail(email);

        if(usuario != null)
            throw new RegraNegocioException("O Endereço de Email já está sendo utilizado.");
    }

    public void telefoneExists(String telefone) throws SQLException {
        Usuario usuario = usuarioRepository.buscarPorTelefone(telefone);

        if(usuario != null)
            throw new RegraNegocioException("O Número de Telefone já está sendo utilizado.");
    }

    public void autenticar(String email, String password) throws SQLException {
        Usuario user = usuarioRepository.buscarPorEmail(email);

        if (user == null)
            throw new RegraNegocioException("Usuário ou senha incorretos!");

        if(!user.getSenha().equals(password))
            throw new RegraNegocioException("Usuário ou senha incorretos!");
    }
}
