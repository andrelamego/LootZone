package lamego.lootzone.infrastructure.repositories;

import lamego.lootzone.application.interfaces.IRepository;
import lamego.lootzone.domain.entities.Pagamento;
import lamego.lootzone.infrastructure.database.IDBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PagamentoRepository implements IRepository<Pagamento> {

    Connection c;
    IDBConnection dbconnection;

    public PagamentoRepository(IDBConnection dbconnection) throws SQLException, ClassNotFoundException {
        this.dbconnection = dbconnection;
        c = dbconnection.getConnection();
    }

    @Override
    public void salvar(Pagamento entidade) throws SQLException {
        String sql = "INSERT INTO t_pagamentos (Valor, Data, Status, MetodoPgtoId) VALUES (?,?,?,?)";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setFloat(1, entidade.getValor());
        ps.setString(2, entidade.getData().toString());
        ps.setString(3, entidade.getStatus());
        ps.setLong(4, entidade.getMetodo().getId());
        ps.executeUpdate();

        ResultSet keys = ps.getGeneratedKeys();
        if(keys.next()){
            entidade.setId(keys.getLong(1));
        }

        keys.close();
        ps.close();
    }

    @Override
    public void atualizar(Pagamento entidade) throws SQLException {
        String sql = "UPDATE Valor = ?, Data = ?, Status = ?, MetodoPgtoId = ? FROM t_pagamentos WHERE Id = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setFloat(1, entidade.getValor());
        ps.setString(2, entidade.getData().toString());
        ps.setString(3, entidade.getStatus());
        ps.setLong(4, entidade.getMetodo().getId());
        ps.setLong(5, entidade.getId());

        ps.execute();
        ps.close();
    }

    @Override
    public void excluir(Pagamento entidade) throws SQLException {
        String sql = "DELETE t_pagamentos WHERE Id = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setLong(1, entidade.getId());
        ps.execute();
        ps.close();
    }

    @Override
    public Pagamento buscar(Pagamento entidade) throws SQLException, ClassNotFoundException {
        MetodoPgtoRepository metodoPgtoRepository = new MetodoPgtoRepository(dbconnection);
        String sql = "SELECT Valor, Data, Status, MetodoPgtoId FROM t_pagamentos WHERE Id = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setLong(1, entidade.getId());
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            entidade.setValor(rs.getFloat(1));
            entidade.setData(LocalDateTime.parse(rs.getString(2)));
            entidade.setStatus(rs.getString(3));
            entidade.setMetodo(metodoPgtoRepository.buscar(rs.getLong(4)));

            return entidade;
        }

        return null;
    }

    @Override
    public List<Pagamento> listar() throws SQLException, ClassNotFoundException {
        MetodoPgtoRepository metodoPgtoRepository = new MetodoPgtoRepository(dbconnection);
        List<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT * FROM t_pagamentos";
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            Pagamento pgto = new Pagamento();
            pgto.setId(rs.getLong(1));
            pgto.setValor(rs.getFloat(2));
            pgto.setData(LocalDateTime.parse(rs.getString(3)));
            pgto.setStatus(rs.getString(4));
            pgto.setMetodo(metodoPgtoRepository.buscar(rs.getLong(5)));

            pagamentos.add(pgto);
        }

        return pagamentos;
    }
}
