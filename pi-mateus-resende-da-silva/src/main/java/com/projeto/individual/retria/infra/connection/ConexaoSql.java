package com.projeto.individual.retria.infra.connection;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexaoSql {

    private final JdbcTemplate connection;
    public ConexaoSql() {

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        dataSource.setUrl("jdbc:sqlserver://umms-retria.database.windows.net:1433;database=BD-UMMS");

        dataSource.setUsername("adm");

        dataSource.setPassword("#Gfgrupo3");

        this.connection = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getConnection() {
        return connection;
    }

}
