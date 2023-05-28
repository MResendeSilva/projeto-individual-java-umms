package com.projeto.individual.retria.repository;

import com.projeto.individual.retria.domain.maquina.Administrador;
import com.projeto.individual.retria.domain.maquina.Empresa;
import com.projeto.individual.retria.infra.connection.ConexaoMySql;
import com.projeto.individual.retria.infra.connection.ConexaoSql;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserRepository {

    private Empresa empresa;
    private Administrador administrador;

    JdbcTemplate con;
    JdbcTemplate conMysql;

    public UserRepository() {
        ConexaoSql conexao = new ConexaoSql();
        con = conexao.getConnection();
        ConexaoMySql conexaoMysql = new ConexaoMySql();
        conMysql = conexaoMysql.getConnection();
    }

    public Boolean existsAdministradorByEmailAndSenha(String email, String senha) {

        List<Administrador> administrador = con.query(String.format("""
                        SELECT 
                            *
                        FROM 
                            administrador
                        WHERE
                            email_administrador = '%s'
                        AND senha_administrador = '%s';
                        """, email, senha),
                new BeanPropertyRowMapper(Administrador.class));

        if (!administrador.isEmpty()) {
            Administrador dados = administrador.get(0);

            verifyLocalAdministrador();
            getEmpresaByAdministrador();

            this.administrador = new Administrador(dados.getIdAdministrador(), dados.getNomeAdministrador(),
                    dados.getEmailAdministrador(), dados.getSenhaAdministrador(),dados.getTelefoneAdministrador(),
                    dados.getChaveSegurancaAdministrador(), dados.getFkOcupacao(), dados.getFkEmpresa());
            return true;
        }

        System.out.println("Email e/ou senha invalidos");
        return false;
    }

    public void verifyLocalAdministrador() {
        List<Administrador> administradoresLocal = conMysql.query(String.format("""
                        SELECT 
                            *
                        FROM 
                            administrador
                        WHERE
                            email_administrador = '%s'
                        AND senha_administrador = '%s';
                        """, administrador.getEmailAdministrador(), administrador.getSenhaAdministrador()),
                new BeanPropertyRowMapper(Administrador.class));

        if (!administradoresLocal.isEmpty()) {
            conMysql.execute(String.format("insert into administrador values"
                            + " (%d, '%s', '%s', '%s', '%s', '%s', %d, %d)",
                    administrador.getIdAdministrador(), administrador.getNomeAdministrador(),
                    administrador.getEmailAdministrador(), administrador.getSenhaAdministrador(),administrador.getTelefoneAdministrador(),
                    administrador.getChaveSegurancaAdministrador(), administrador.getFkOcupacao(), administrador.getFkEmpresa()));
        }

    }

    public void getEmpresaByAdministrador() {
        List<Empresa> empresas = con.query(
                String.format("""
                              SELECT
                                *
                              FROM
                                empresa
                              WHERE 
                                id_empresa = %d;
                              """, administrador.getFkEmpresa()),
                new BeanPropertyRowMapper(Empresa.class));

        Empresa dados = empresas.get(0);

        this.empresa = new Empresa(dados.getIdEmpresa(), dados.getNomeEmpresa(),dados.getCnpj(), dados.getTelefone01(),
                dados.getTelefone02(), dados.getEmail(), dados.getResponsavelEmpresa(), dados.getFkMatriz());

        List<Empresa> empresasLocal = conMysql.query(
                String.format("""
                              SELECT
                                *
                              FROM
                                empresa
                              WHERE 
                                id_empresa = %d;
                              """, administrador.getFkEmpresa()),
                new BeanPropertyRowMapper(Empresa.class));

        if (empresasLocal.isEmpty()) {
            conMysql.execute(String.format("""
                            INSERT INTO empresa values
                            (%d, '%s', '%s', '%s', '%s', '%s', '%s', %d);
                            """,
                    empresa.getIdEmpresa(),
                    empresa.getNomeEmpresa(),
                    empresa.getCnpj(),
                    empresa.getTelefone01(),
                    empresa.getTelefone02(),
                    empresa.getEmail(),
                    empresa.getResponsavelEmpresa(),
                    empresa.getFkMatriz())
            );
        }
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Administrador getAdministrador() {
        return administrador;
    }
}
