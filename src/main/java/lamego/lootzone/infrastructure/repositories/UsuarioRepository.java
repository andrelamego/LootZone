package lamego.lootzone.infrastructure.repositories;

import lamego.lootzone.application.interfaces.IRepository;
import lamego.lootzone.domain.entities.*;
import lamego.lootzone.infrastructure.database.IDBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository implements IRepository<Usuario> {

    private Connection c;
    private IDBConnection dbConnection;

    public UsuarioRepository(IDBConnection dbConnection) throws SQLException, ClassNotFoundException {
        this.dbConnection = dbConnection;
        c = dbConnection.getConnection();
    }

    @Override
    public void salvar(Usuario entidade) throws SQLException{
        String sql = "INSERT INTO t_usuarios (Nome, Sobrenome, Email, Senha, Telefone, DataNascimento) VALUES(?,?,?,?,?,?)";
        PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, entidade.getNome());
        ps.setString(2, entidade.getSobrenome());
        ps.setString(3, entidade.getEmail());
        ps.setString(4, entidade.getSenha());
        ps.setString(5, entidade.getTelefone());
        ps.setDate(6, Date.valueOf(entidade.getDataNascimento()));
        ps.executeUpdate();

        //DEFINIR ID AUTOMATICAMENTE
        ResultSet keys = ps.getGeneratedKeys();
        if(keys.next()) {
            entidade.setId(keys.getLong(1));
        }
        ps.close();

        System.out.println(entidade.getId());

        //INSERIR NA TABELA CORRESPONDENTE AO TIPO
        switch (entidade) {
            case Comprador comprador -> {
                String sqlComprador = "INSERT INTO t_compradores (UsuarioId, Credito) VALUES (?,?)";
                PreparedStatement psComprador = c.prepareStatement(sqlComprador);
                psComprador.setLong(1, comprador.getId());
                psComprador.setFloat(2, comprador.getCredito());
                psComprador.executeUpdate();
                
                psComprador.close();
            }
            case VendedorPF vendedorPF -> {
                String sqlVendedor = "INSERT INTO t_vendedores (UsuarioId) VALUES (?)";
                PreparedStatement psVendedor = c.prepareStatement(sqlVendedor);

                psVendedor.setLong(1, vendedorPF.getId());
                psVendedor.executeUpdate();

                String sqlPF = "INSERT INTO t_vendedoresPF (VendedorId, CPF) VALUES (?,?)";
                PreparedStatement psPF = c.prepareStatement(sqlPF);

                psPF.setLong(1, vendedorPF.getId());
                psPF.setString(2, vendedorPF.getCpf());
                psPF.executeUpdate();
                
                psPF.close();
            }
            case VendedorPJ vendedorPJ -> {
                String sqlVendedor = "INSERT INTO t_vendedores (UsuarioId) VALUES (?)";
                PreparedStatement psVendedor = c.prepareStatement(sqlVendedor);

                psVendedor.setLong(1, vendedorPJ.getId());
                psVendedor.executeUpdate();

                String sqlPJ = "INSERT INTO t_vendedoresPJ (VendedorId, CNPJ) VALUES (?,?)";
                PreparedStatement psPJ = c.prepareStatement(sqlPJ);

                psPJ.setLong(1, vendedorPJ.getId());
                psPJ.setString(2, vendedorPJ.getCnpj());
                psPJ.executeUpdate();
                
                psPJ.close();
            }
            default -> throw new IllegalStateException("Unexpected value: " + entidade);
        }
    }

    @Override
    public void atualizar(Usuario entidade) throws SQLException {
        String sql = "UPDATE t_usuarios SET Nome = ?, Sobrenome = ?, Email = ?, Telefone = ?, DataNascimento = ? WHERE Id = ? ";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setString(1, entidade.getNome());
        ps.setString(2, entidade.getSobrenome());
        ps.setString(3, entidade.getEmail());
        ps.setString(4, entidade.getTelefone());
        ps.setDate(5, Date.valueOf(entidade.getDataNascimento()));

        ps.setLong(6, entidade.getId());
        ps.executeUpdate();

        switch (entidade) {
            case Comprador comprador -> {
                String sqlComprador = "UPDATE t_compradores SET Credito = ? WHERE UsuarioId = ?";
                PreparedStatement psComprador = c.prepareStatement(sqlComprador);

                psComprador.setFloat(1, comprador.getCredito());
                psComprador.setLong(2, comprador.getId());
                ps.executeUpdate();
            }
            case VendedorPF vendedorPF -> {
                String sqlPF = "UPDATE t_vendedoresPF SET CPF = ? WHERE UsuarioId = ?";
                PreparedStatement psPF = c.prepareStatement(sqlPF);

                psPF.setString(1, vendedorPF.getCpf());
                psPF.setLong(2, vendedorPF.getId());
                psPF.executeUpdate();
            }
            case VendedorPJ vendedorPJ -> {
                String sqlPJ = "UPDATE t_vendedoresPF SET CNPJ = ? WHERE UsuarioId = ?";
                PreparedStatement psPJ = c.prepareStatement(sqlPJ);

                psPJ.setString(1, vendedorPJ.getCnpj());
                psPJ.setLong(2, vendedorPJ.getId());
                psPJ.executeUpdate();
            }
            default -> {
            }
        }
    }

    @Override
    public void excluir(Usuario entidade) throws SQLException {
        String sql = "DELETE t_usuarios WHERE Id = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        if (entidade instanceof Comprador comprador) {
            String sqlComprador = "DELETE t_compradores WHERE UsuarioId = ?";
            PreparedStatement psComprador = c.prepareStatement(sqlComprador);

            psComprador.setLong(1, comprador.getId());
            psComprador.executeUpdate();
            psComprador.close();
        }
        else if (entidade instanceof VendedorPF vendedorPF) {
            String sqlPF = "DELETE t_vendedoresPF WHERE UsuarioId = ?";
            PreparedStatement psPF = c.prepareStatement(sqlPF);

            psPF.setLong(1, vendedorPF.getId());
            psPF.executeUpdate();
            psPF.close();

            String sqlVendedores = "DELETE t_vendedores WHERE UsuarioId = ?";
            PreparedStatement psVendedores = c.prepareStatement(sqlVendedores);

            psVendedores.setLong(1, vendedorPF.getId());
            psVendedores.executeUpdate();
            psVendedores.close();
        }
        else if (entidade instanceof VendedorPJ vendedorPJ) {
            String sqlPJ = "DELETE t_vendedoresPJ WHERE UsuarioId = ?";
            PreparedStatement psPJ = c.prepareStatement(sqlPJ);

            psPJ.setLong(1, vendedorPJ.getId());
            psPJ.executeUpdate();
            psPJ.close();

            String sqlVendedores = "DELETE t_vendedores WHERE UsuarioId = ?";
            PreparedStatement psVendedores = c.prepareStatement(sqlVendedores);

            psVendedores.setLong(1, vendedorPJ.getId());
            psVendedores.executeUpdate();
            psVendedores.close();
        }

        ps.setLong(1, entidade.getId());
        ps.execute();
        ps.close();
    }

    @Override
    public Usuario buscar(Usuario entidade) throws SQLException {
        String sql = "SELECT Id, Nome, Sobrenome, Email, Telefone, DataNascimento FROM t_usuarios WHERE Id = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setLong(1, entidade.getId());

        int cont = 0;
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            entidade.setNome(rs.getString("Nome"));
            entidade.setSobrenome(rs.getString("Sobrenome"));
            entidade.setEmail(rs.getString("Email"));
            entidade.setTelefone(rs.getString("Telefone"));
            entidade.setDataNascimento(rs.getDate("DataNascimento").toLocalDate());
            cont++;
        }
        else {
            //usuario nao encontrado;
        }

        if(cont == 0){
            switch (entidade) {
                case Comprador comprador -> entidade = new Comprador();
                case VendedorPF vendedorPF -> entidade = new VendedorPF();
                case VendedorPJ vendedorPJ -> entidade = new VendedorPJ();
                default -> { }
            }
        }

        rs.close();
        ps.close();
        return entidade;
    }

    public Usuario buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT Id, Nome, Sobrenome, Senha, Telefone, DataNascimento FROM t_usuarios WHERE Email = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            Usuario user = new Usuario();
            user.setId(rs.getLong(1));
            user.setNome(rs.getString(2));
            user.setSobrenome(rs.getString(3));
            user.setEmail(email);
            user.setSenha(rs.getString(4));
            user.setTelefone(rs.getString(5));
            user.setDataNascimento(rs.getDate(6).toLocalDate());

            return user;
        }

        return null;
    }

    public Usuario buscarPorTelefone(String telefone) throws SQLException {
        String sql = "SELECT Id, Nome, Sobrenome, Email, Senha, DataNascimento FROM t_usuarios WHERE Telefone = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setString(1, telefone);
        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            Usuario user = new Usuario();
            user.setId(rs.getLong(1));
            user.setNome(rs.getString(2));
            user.setSobrenome(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setSenha(rs.getString(5));
            user.setTelefone(telefone);
            user.setDataNascimento(rs.getDate(6).toLocalDate());

            return user;
        }

        return null;
    }

    public Vendedor buscarVendedor(long id) throws SQLException {
        String sql = "SELECT Nome, Sobrenome, Email, Telefone, DataNascimento FROM t_usuarios WHERE Id = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();


        //Procurar na Tabela VendedorPF
        String sqlPF = "SELECT CPF FROM t_vendedoresPF WHERE UsuarioId = ?";
        PreparedStatement psPF = c.prepareStatement(sqlPF);
        psPF.setLong(1, id);
        ResultSet rsPF = psPF.executeQuery();

        if(rsPF.next()) {
            VendedorPF vendedor = new VendedorPF();

            vendedor.setId(id);
            vendedor.setNome(rs.getString("Nome"));
            vendedor.setSobrenome(rs.getString("Sobrenome"));
            vendedor.setEmail(rs.getString("Email"));
            vendedor.setTelefone(rs.getString("Telefone"));
            vendedor.setDataNascimento(rs.getDate("DataNascimento").toLocalDate());
            vendedor.setCpf(rsPF.getString(1));

            return vendedor;
        }

        //Procurar na Tabela VendedorPJ
        String sqlPJ = "SELECT CNPJ FROM t_vendedoresPJ WHERE UsuarioId = ?";
        PreparedStatement psPJ = c.prepareStatement(sqlPJ);
        psPF.setLong(1, id);
        ResultSet rsPJ = psPJ.executeQuery();

        if(rsPJ.next()) {
            VendedorPJ vendedor = new VendedorPJ();

            vendedor.setId(id);
            vendedor.setNome(rs.getString("Nome"));
            vendedor.setSobrenome(rs.getString("Sobrenome"));
            vendedor.setEmail(rs.getString("Email"));
            vendedor.setTelefone(rs.getString("Telefone"));
            vendedor.setDataNascimento(rs.getDate("DataNascimento").toLocalDate());
            vendedor.setCnpj(rsPJ.getString(1));

            return vendedor;
        }

        return null;
    }

    @Override
    public List<Usuario> listar() throws SQLException{
        String sql = "SELECT Id, Nome, Sobrenome, Email, Telefone, DataNascimento FROM t_usuarios";
        PreparedStatement ps = c.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        List<Usuario> lista = new ArrayList<>();

        while(rs.next()){
            boolean adicionado = false;

            //Procurar na tabela Compradores
            if(!adicionado) {
                String sqlComprador = "SELECT Credito FROM t_compradores WHERE UsuarioId = ?";
                PreparedStatement psComprador = c.prepareStatement(sqlComprador);
                psComprador.setLong(1, rs.getLong("Id"));
                ResultSet rsComprador = psComprador.executeQuery();

                if (rsComprador.next()) {
                    Comprador comprador = new Comprador();

                    comprador.setId(rs.getLong("Id"));
                    comprador.setNome(rs.getString("Nome"));
                    comprador.setSobrenome(rs.getString("Sobrenome"));
                    comprador.setEmail(rs.getString("Email"));
                    comprador.setTelefone(rs.getString("Telefone"));
                    comprador.setDataNascimento(rs.getDate("DataNascimento").toLocalDate());
                    comprador.setCredito(rsComprador.getFloat(1));

                    lista.add(comprador);
                    adicionado = true;
                }
            }

            //Procurar na Tabela VendedorPF
            if(!adicionado) {
                String sqlPF = "SELECT CPF FROM t_vendedoresPF WHERE UsuarioId = ?";
                PreparedStatement psPF = c.prepareStatement(sqlPF);
                psPF.setLong(1, rs.getLong("Id"));
                ResultSet rsPF = psPF.executeQuery();

                if (rsPF.next()) {
                    VendedorPF vendedor = new VendedorPF();

                    vendedor.setId(rs.getLong("Id"));
                    vendedor.setNome(rs.getString("Nome"));
                    vendedor.setSobrenome(rs.getString("Sobrenome"));
                    vendedor.setEmail(rs.getString("Email"));
                    vendedor.setTelefone(rs.getString("Telefone"));
                    vendedor.setDataNascimento(rs.getDate("DataNascimento").toLocalDate());
                    vendedor.setCpf(rsPF.getString(1));

                    lista.add(vendedor);
                    adicionado = true;
                }
            }

            //Procurar na Tabela VendedorPJ
            if(!adicionado) {
                String sqlPJ = "SELECT CNPJ FROM t_vendedoresPJ WHERE UsuarioId = ?";
                PreparedStatement psPJ = c.prepareStatement(sqlPJ);
                psPJ.setLong(1, rs.getLong("Id"));
                ResultSet rsPJ = psPJ.executeQuery();

                if (rsPJ.next()) {
                    VendedorPJ vendedor = new VendedorPJ();

                    vendedor.setId(rs.getLong("Id"));
                    vendedor.setNome(rs.getString("Nome"));
                    vendedor.setSobrenome(rs.getString("Sobrenome"));
                    vendedor.setEmail(rs.getString("Email"));
                    vendedor.setTelefone(rs.getString("Telefone"));
                    vendedor.setDataNascimento(rs.getDate("DataNascimento").toLocalDate());
                    vendedor.setCnpj(rsPJ.getString(1));

                    lista.add(vendedor);
                    adicionado = true;
                }
            }
        }

        return lista;
    }
}
