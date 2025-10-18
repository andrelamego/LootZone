package lamego.lootzone.infrastructure.repositories;

import lamego.lootzone.application.interfaces.IProdutoRepository;
import lamego.lootzone.application.interfaces.IRepository;
import lamego.lootzone.domain.entities.Item;
import lamego.lootzone.domain.entities.Jogo;
import lamego.lootzone.domain.entities.Produto;
import lamego.lootzone.infrastructure.database.IDBConnection;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProdutoRepository implements IProdutoRepository<Produto> {

    Connection c;
    IDBConnection dbconnection;

    public ProdutoRepository(IDBConnection dbconnection) throws SQLException, ClassNotFoundException {
        this.dbconnection = dbconnection;
        c = dbconnection.getConnection();
    }

    @Override
    public void salvar(Produto entidade) throws SQLException {
        String sql = "INSERT INTO t_produtos (Nome, Descricao, Valor, VendedorId, CategoriaId) VALUES (?,?,?,?,?)";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setString(1, entidade.getNome());
        ps.setString(2, entidade.getDescricao());
        ps.setFloat(3, entidade.getValor());
        ps.setLong(4, entidade.getVendedor().getId());
        ps.setLong(5, entidade.getCategoria().getId());
        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        if(keys.next()) {
            entidade.setId(keys.getLong(1));
        }

        if (entidade instanceof Jogo jogo) {
            String sqlJogo = "INSERT INTO t_jogos (ProdutoId, Key) VALUES (?,?)";
            PreparedStatement psJogo = c.prepareStatement(sqlJogo);

            psJogo.setLong(1, jogo.getId());
            psJogo.setString(2,jogo.getKey());
            psJogo.executeUpdate();

            psJogo.close();
        }
        else if (entidade instanceof Item item){
            String sqlItem = "INSERT INTO t_itens (ProdutoId, Quantidade) VALUES (?,?)";
            PreparedStatement psItem = c.prepareStatement(sqlItem);

            psItem.setLong(1, item.getId());
            psItem.setInt(2, item.getQuantidade());
            psItem.executeUpdate();

            psItem.close();
        }

        ps.close();
    }

    @Override
    public void atualizar(Produto entidade) throws SQLException {
        String sql = "UPDATE t_produtos SET Nome = ?, Descricao = ?, Valor = ?, VendedorId = ?, CategoriaId = ? WHERE Id = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setString(1, entidade.getNome());
        ps.setString(2, entidade.getDescricao());
        ps.setFloat(3, entidade.getValor());
        ps.setLong(4, entidade.getVendedor().getId());
        ps.setLong(5, entidade.getCategoria().getId());

        ps.setLong(6, entidade.getId());
        ps.executeUpdate();

        if(entidade instanceof Jogo jogo) {
            String sqlJogo = "UPDATE t_jogos SET Key = ? WHERE ProdutoId = ?";
            PreparedStatement psJogo = c.prepareStatement(sqlJogo);

            psJogo.setString(1, jogo.getKey());
            psJogo.setLong(2, jogo.getId());

            psJogo.executeUpdate();
            psJogo.close();
        }
        else if (entidade instanceof Item item) {
            String sqlItem = "UPDATE t_itens SET Quantidade = ? WHERE ProdutoId = ?";
            PreparedStatement psItem = c.prepareStatement(sqlItem);

            psItem.setInt(1, item.getQuantidade());
            psItem.setLong(2, item.getId());

            psItem.executeUpdate();
            psItem.close();
        }

        ps.close();
    }

    @Override
    public void excluir(Produto entidade) throws SQLException {
        String sql = "DELETE t_produtos WHERE Id = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        if (entidade instanceof Jogo jogo) {
            String sqlJogo = "DELETE t_jogos WHERE ProdutoId = ?";
            PreparedStatement psJogo = c.prepareStatement(sqlJogo);

            psJogo.setLong(1, jogo.getId());
            psJogo.executeUpdate();

            psJogo.close();
        }
        else if (entidade instanceof Item item) {
            String sqlItem = "DELETE t_itens WHERE ProdutoId = ?";
            PreparedStatement psItem = c.prepareStatement(sqlItem);

            psItem.setLong(1, item.getId());
            psItem.executeUpdate();

            psItem.close();
        }

        ps.setLong(1, entidade.getId());
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public Produto buscar(Produto entidade) throws SQLException, ClassNotFoundException {
        UsuarioRepository usuarioRepository = new UsuarioRepository(dbconnection);
        CategoriaRepository categoriaRepository = new CategoriaRepository(dbconnection);

        String sql = "SELECT Nome, Descricao, Valor, VendedorId, CategoriaId FROM t_produtos WHERE Id = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setLong(1, entidade.getId());
        ResultSet rs = ps.executeQuery();

        int cont = 0;
        if(rs.next()) {
            entidade.setNome(rs.getString(1));
            entidade.setDescricao(rs.getString(2));
            entidade.setValor(rs.getFloat(3));
            entidade.setVendedor(usuarioRepository.buscarVendedor(rs.getLong(4)));
            entidade.setCategoria(categoriaRepository.buscar(rs.getLong(5)));
        }

        if (entidade instanceof Jogo jogo){
            String sqlJogo = "SELECT Key FROM t_jogos WHERE ProdutoId = ?";
            PreparedStatement psJogo = c.prepareStatement(sqlJogo);

            psJogo.setLong(1, jogo.getId());
            ResultSet rsJogo = psJogo.executeQuery();

            if(rsJogo.next()) {
                jogo.setKey(rsJogo.getString(1));
            }
        }
        else if (entidade instanceof Item item) {
            String sqlItem = "SELECT Quantidade FROM t_itens WHERE ProdutoId = ?";
            PreparedStatement psItem = c.prepareStatement(sqlItem);

            psItem.setLong(1, item.getId());
            ResultSet rsItem = psItem.executeQuery();

            if(rsItem.next()) {
                item.setQuantidade(rs.getInt(1));
            }
        }

        return entidade;
    }

    @Override
    public Jogo buscar(Jogo jogo) throws SQLException, ClassNotFoundException {
        UsuarioRepository usuarioRepository = new UsuarioRepository(dbconnection);
        CategoriaRepository categoriaRepository = new CategoriaRepository(dbconnection);

        String sql = "SELECT Nome, Descricao, Valor, VendedorId, CategoriaId FROM t_produtos WHERE Id = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setLong(1, jogo.getId());
        ResultSet rs = ps.executeQuery();


        String sqlJogo = "SELECT Key FROM t_jogos WHERE ProdutoId = ?";
        PreparedStatement psJogo = c.prepareStatement(sqlJogo);

        psJogo.setLong(1, jogo.getId());
        ResultSet rsJogo = psJogo.executeQuery();

        int cont = 0;
        if(rs.next()) {
            jogo.setNome(rs.getString(1));
            jogo.setDescricao(rs.getString(2));
            jogo.setValor(rs.getFloat(3));
            jogo.setVendedor(usuarioRepository.buscarVendedor(rs.getLong(4)));
            jogo.setCategoria(categoriaRepository.buscar(rs.getLong(5)));
        }

        if(rsJogo.next()) {
            jogo.setKey(rsJogo.getString(1));
        }

        return jogo;
    }

    @Override
    public Item buscar(Item item) throws SQLException, ClassNotFoundException {
        UsuarioRepository usuarioRepository = new UsuarioRepository(dbconnection);
        CategoriaRepository categoriaRepository = new CategoriaRepository(dbconnection);

        //PRODUTO
        String sql = "SELECT Nome, Descricao, Valor, VendedorId, CategoriaId FROM t_produtos WHERE Id = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setLong(1, item.getId());
        ResultSet rs = ps.executeQuery();

        //ITEM
        String sqlItem = "SELECT Quantidade FROM t_itens WHERE ProdutoId = ?";
        PreparedStatement psItem = c.prepareStatement(sqlItem);

        psItem.setLong(1, item.getId());
        ResultSet rsItem = psItem.executeQuery();

        int cont = 0;
        if(rs.next()) {
            item.setNome(rs.getString(1));
            item.setDescricao(rs.getString(2));
            item.setValor(rs.getFloat(3));
            item.setVendedor(usuarioRepository.buscarVendedor(rs.getLong(4)));
            item.setCategoria(categoriaRepository.buscar(rs.getLong(5)));
        }

        if(rsItem.next()) {
            item.setQuantidade(rs.getInt(1));
        }

        return item;
    }

    @Override
    public List<Produto> listar() {
        return List.of();
    }


    @Override
    public void venderItem(Produto entidade, int qnt) throws SQLException {
        String sql = "SELECT Quantidade FROM t_itens WHERE Id = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setLong(1, entidade.getId());

        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            if (rs.getInt("Quantidade") >= qnt && entidade instanceof Item item) {
                item.setQuantidade(item.getQuantidade() - qnt);
                atualizar(item);
            }
        }
    }
}
