package com.projeto.individual.retria.infra.connection;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexaoMySql {

    private final JdbcTemplate connection;

    public ConexaoMySql() {

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        //dataSource.setUrl("jdbc:mysql://localhost:3306/retria");
        dataSource.setUrl("jdbc:mysql://0.0.0.0:3306/retria?autoReconnect=true&useSSL=false");

        dataSource.setUsername("root");

        dataSource.setPassword("urubu100");

        this.connection = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getConnection() {
        return connection;
    }
}
