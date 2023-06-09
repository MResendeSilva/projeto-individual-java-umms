package com.projeto.individual.retria.repository;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import com.projeto.individual.retria.domain.maquina.*;
import com.projeto.individual.retria.infra.componentes.TipoComponente;
import com.projeto.individual.retria.infra.connection.ConexaoMySql;
import com.projeto.individual.retria.infra.connection.ConexaoSql;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class MaquinaRepository {
    private MaquinaUltrassom maquinaUltrassom;
    private List<EspecificacaoComponente> especificacoesComponente = new ArrayList<>();
    private List<MaquinaUltrassomEspec> maquinasUltrassomEspecs = new ArrayList<>();
    private List<RedeInterface> redeAtual = new ArrayList<>();
    private Webhook webhook = new Webhook();
    static Looca looca = new Looca();

    JdbcTemplate con;
    JdbcTemplate conMysql;

    public MaquinaRepository() {
        ConexaoSql conexao = new ConexaoSql();
        con = conexao.getConnection();
        ConexaoMySql conexaoMysql = new ConexaoMySql();
        conMysql = conexaoMysql.getConnection();
    }

    public void getMaquinaDeUltrassomById(Integer fkAdmin, Integer fkEmpresa) {
        List<MaquinaUltrassom> maquinasUltra = con.query(String.format("""
                        select 
                            m.* 
                        from 
                            maquina_ultrassom as m
                        where 
                            m.numero_serial_maquina = '%s';
                        """, looca.getProcessador().getId()),
                new BeanPropertyRowMapper(MaquinaUltrassom.class));

        List<MaquinaUltrassom> maquinasUltraLocal = conMysql.query(String.format("""
                        select 
                            m.* 
                        from 
                            maquina_ultrassom as m
                        where 
                            m.numero_serial_maquina = '%s';
                        """, looca.getProcessador().getId()),
                new BeanPropertyRowMapper(MaquinaUltrassom.class));

        while (maquinasUltra.size() == 0) {
            con.execute(String.format("""
                    insert into maquina_ultrassom
                        (sistema_operacional, numero_serial_maquina, fk_administrador,fk_empresa) 
                    values
                        ('%s','%s' ,%d, %d);
                    """, looca.getSistema().getSistemaOperacional(), looca.getProcessador().getId(), fkAdmin, fkEmpresa));

            maquinasUltra = con.query(String.format("""
                            select 
                                m.* 
                            from 
                                maquina_ultrassom as m
                            where 
                                m.numero_serial_maquina = '%s';
                            """, looca.getProcessador().getId()),
                    new BeanPropertyRowMapper(MaquinaUltrassom.class));

        }
        System.out.println("Maquina");
        System.out.println(maquinasUltra.get(0));
        MaquinaUltrassom dados = maquinasUltra.get(0);
        while (maquinasUltraLocal.size() == 0) {

            conMysql.execute(String.format("""
                    insert into maquina_ultrassom
                        (id_maquina,sistema_operacional, numero_serial_maquina, fk_administrador,fk_empresa) 
                    values
                        (%d,'%s','%s' ,%d, %d);
                    """, dados.getIdMaquina(), looca.getSistema().getSistemaOperacional(), looca.getProcessador().getId(), fkAdmin, fkEmpresa));

            maquinasUltraLocal = conMysql.query(String.format("""
                            select 
                                m.* 
                            from 
                                maquina_ultrassom as m
                            where 
                                m.numero_serial_maquina = '%s';
                            """, looca.getProcessador().getId()),
                    new BeanPropertyRowMapper(MaquinaUltrassom.class));
        }
        System.out.println(dados);

        maquinaUltrassom = new MaquinaUltrassom(dados.getIdMaquina(), dados.getSistemaOperacional(), dados.getNumeroSerialMaquina(),
                dados.getStatusMaquina(), dados.getStatusConexao(), dados.getFkAdministrador(), dados.getFkEmpresa());
    }

    public void setComponente(Processador processador) {
        List<EspecificacaoComponente> especificacaoComponentes =
                con.query(String.format("""
                        select
                            *
                        from
                            especificacao_componente
                        where
                            descricao_componente = '%s'
                        and
                            numero_serial = '%s'
                        """, processador.getNome(), processador.getId()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        List<EspecificacaoComponente> especificacaoComponentesLocal =
                conMysql.query(String.format("""
                        select
                            *
                        from
                            especificacao_componente
                        where
                            descricao_componente = '%s'
                        and
                            numero_serial = '%s'
                        """, processador.getNome(), processador.getId()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        if (especificacaoComponentes.isEmpty()) {
            con.execute(String.format("insert into especificacao_componente" +
                            "(tipo_componente, descricao_componente, nome_fabricante, numero_serial) values ('%s', '%s', '%s', '%s')",
                    "CPU", processador.getNome(), processador.getFabricante(), processador.getId()));

            especificacaoComponentes =
                    con.query(String.format("""
                            select
                                *
                            from
                                especificacao_componente
                            where
                                descricao_componente = '%s'
                            and
                                numero_serial = '%s'
                            """, processador.getNome(), processador.getId()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));
        }

        EspecificacaoComponente dados = especificacaoComponentes.get(0);

        System.out.println("Dados do processador");
        System.out.println(dados);

        if (especificacaoComponentesLocal.isEmpty()) {

            conMysql.execute(String.format("insert into especificacao_componente" +
                            "(id_especificacao_componente,tipo_componente, descricao_componente, nome_fabricante, numero_serial) " +
                            "values (%d,'%s', '%s', '%s', '%s')",
                    dados.getIdEspecificacaoComponente(), "CPU", processador.getNome(), processador.getFabricante(), processador.getId()));
        }

        especificacoesComponente.add(new EspecificacaoComponente(dados.getIdEspecificacaoComponente(),
                dados.getTipoComponente(), dados.getNomeFabricante(), dados.getDescricaoComponente(), dados.getNumeroSerial()));

    }

    public void setComponente(Memoria memoria) {
        String nomeMemoria = String.format("Pente de memória ram - %.0f GB",
                convertBytesToGB(memoria.getTotal()));

        List<EspecificacaoComponente> especificacaoComponentes =
                con.query(String.format("""
                        select
                            *
                        from
                            especificacao_componente
                        where
                            descricao_componente = '%s'
                        """, nomeMemoria), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        List<EspecificacaoComponente> especificacaoComponentesLocal =
                conMysql.query(String.format("""
                        select
                            *
                        from
                            especificacao_componente
                        where
                            descricao_componente = '%s'
                        """, nomeMemoria), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        if (especificacaoComponentes.isEmpty()) {
            con.execute(String.format("insert into especificacao_componente" +
                            "(tipo_componente, descricao_componente) values ('%s','%s')",
                    "RAM", nomeMemoria));

            especificacaoComponentes =
                    con.query(String.format("""
                            select
                                *
                            from
                                especificacao_componente
                            where
                                descricao_componente = '%s'
                            """, nomeMemoria), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        }
        EspecificacaoComponente dados = especificacaoComponentes.get(0);

        System.out.println("Dados da memoria");
        System.out.println(dados);

        if (especificacaoComponentesLocal.size() == 0) {

            conMysql.execute(String.format("insert into especificacao_componente" +
                            "(id_especificacao_componente,tipo_componente, descricao_componente) values (%d,'%s','%s')",
                    dados.getIdEspecificacaoComponente(), "RAM", nomeMemoria));
        }

        especificacoesComponente.add(new EspecificacaoComponente(dados.getIdEspecificacaoComponente(),
                dados.getTipoComponente(), dados.getNomeFabricante(), dados.getDescricaoComponente(), dados.getNumeroSerial()));

    }

    public void setComponente(List<RedeInterface> interfaces) {
        RedeInterface redeAtual = interfaces.stream().
                filter(r -> r.getBytesRecebidos() > 0 && r.getBytesEnviados() > 0).findFirst().get();

        List<EspecificacaoComponente> especificacaoComponentes =
                con.query(String.format("""
                        select
                            *
                        from
                            especificacao_componente
                        where
                            descricao_componente = '%s'
                        and
                            numero_serial = '%s'
                        """, redeAtual.getNomeExibicao(), redeAtual.getEnderecoMac()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        List<EspecificacaoComponente> especificacaoComponentesLocal =
                conMysql.query(String.format("""
                        select
                            *
                        from
                            especificacao_componente
                        where
                            descricao_componente = '%s'
                        and
                            numero_serial = '%s'
                        """, redeAtual.getNomeExibicao(), redeAtual.getEnderecoMac()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        if (especificacaoComponentes.isEmpty()) {
            con.execute(String.format("insert into especificacao_componente" +
                            "(tipo_componente,descricao_componente, numero_serial) values ('%s', '%s','%s')",
                    "REDE", redeAtual.getNomeExibicao(), redeAtual.getEnderecoMac()));

            especificacaoComponentes =
                    con.query(String.format("""
                            select
                                *
                            from
                                especificacao_componente
                            where
                                descricao_componente = '%s'
                            and
                                numero_serial = '%s'
                            """, redeAtual.getNomeExibicao(), redeAtual.getEnderecoMac()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        }
        EspecificacaoComponente dados = especificacaoComponentes.get(0);

        System.out.println("Dados da rede");
        System.out.println(dados);

        if (especificacaoComponentesLocal.isEmpty()) {
            conMysql.execute(String.format("insert into especificacao_componente" +
                            "(id_especificacao_componente,tipo_componente,descricao_componente, numero_serial) values (%d,'%s', '%s','%s')",
                    dados.getIdEspecificacaoComponente(), "REDE", redeAtual.getNomeExibicao(), redeAtual.getEnderecoMac()));
        }

        especificacoesComponente.add(new EspecificacaoComponente(dados.getIdEspecificacaoComponente(),
                dados.getTipoComponente(), dados.getNomeFabricante(), dados.getDescricaoComponente(), dados.getNumeroSerial()));


    }

    public void setComponente(Volume disco) {
        if (convertBytesToGB(disco.getTotal()) >= 1) {

            Boolean isDiscoPresent = especificacoesComponente.stream()
                    .filter(c -> c.getTipoComponente().equals(TipoComponente.DISCO)).findAny().isPresent();


            String nomeDisco = String.format("HD/SSD - %.0f GB", convertBytesToGB(disco.getTotal()));

            List<EspecificacaoComponente> especificacaoComponentes =
                    con.query(String.format("""
                            select
                                *
                            from
                                especificacao_componente
                            where
                                descricao_componente = '%s'
                            and
                                numero_serial = '%s'
                            """, nomeDisco, disco.getUUID()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

            List<EspecificacaoComponente> especificacaoComponentesLocal =
                    conMysql.query(String.format("""
                            select
                                *
                            from
                                especificacao_componente
                            where
                                descricao_componente = '%s'
                            and
                                numero_serial = '%s'
                            """, nomeDisco, disco.getUUID()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

            if (especificacaoComponentes.isEmpty()) {
                con.execute(String.format("insert into especificacao_componente" +
                                "(tipo_componente,descricao_componente, numero_serial) values ('%s', '%s','%s')",
                        "DISCO", nomeDisco, disco.getUUID()));

                especificacaoComponentes =
                        con.query(String.format("""
                                select
                                    *
                                from
                                    especificacao_componente
                                where
                                    descricao_componente = '%s'
                                and
                                    numero_serial = '%s'
                                """, nomeDisco, disco.getUUID()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

            }
            EspecificacaoComponente dados = especificacaoComponentes.get(0);

            if (especificacaoComponentesLocal.isEmpty()) {
                conMysql.execute(String.format("insert into especificacao_componente" +
                                "(id_especificacao_componente,tipo_componente,descricao_componente, numero_serial) values (%d,'%s', '%s','%s')",
                        dados.getIdEspecificacaoComponente(), "DISCO", nomeDisco, disco.getUUID()));
            }

            System.out.println("Dados do disco");
            System.out.println(dados);

            if (!isDiscoPresent) {
                especificacoesComponente.add(new EspecificacaoComponente(dados.getIdEspecificacaoComponente(),
                        dados.getTipoComponente(), dados.getNomeFabricante(), dados.getDescricaoComponente(), dados.getNumeroSerial()));
            } else {
                int cont = 0;

                for (EspecificacaoComponente e : especificacoesComponente) {
                    if (e.getNumeroSerial() != null) {
                        if (e.getNumeroSerial().equalsIgnoreCase(disco.getUUID())){
                            cont++;
                        }
                    }
                }
                if (cont == 0) {
                    especificacoesComponente.add(new EspecificacaoComponente(dados.getIdEspecificacaoComponente(),
                            dados.getTipoComponente(), dados.getNomeFabricante(), dados.getDescricaoComponente(), dados.getNumeroSerial()));
                }
            }
        }

    }


    public void setMaquinaUltrassomEspec(Double usoMaximo, Integer fkMaquina, Integer fkEspecComp) {
        Integer usoMaximotoInt = usoMaximo.intValue();

        List<MaquinaUltrassomEspec> maquinaUltraEspec = con.query(String.format("""
                            select
                                m.*
                            from 
                                maquina_ultrassom_especificada m
                            join 
                                especificacao_componente e
                            on
                                m.fk_especificacao_componente = e.id_especificacao_componente
                            where 
                                uso_maximo = %d
                            and fk_maquina = %d
                            and fk_especificacao_componente = %d                
                        """, usoMaximotoInt, fkMaquina, fkEspecComp),
                new BeanPropertyRowMapper<>(MaquinaUltrassomEspec.class));

        List<MaquinaUltrassomEspec> maquinaUltraEspecLocal = conMysql.query(String.format("""
                            select
                                m.*
                            from 
                                maquina_ultrassom_especificada m
                            join 
                                especificacao_componente e
                            on
                                m.fk_especificacao_componente = e.id_especificacao_componente
                            where 
                                uso_maximo = %d
                            and fk_maquina = %d
                            and fk_especificacao_componente = %d                
                        """, usoMaximotoInt, fkMaquina, fkEspecComp),
                new BeanPropertyRowMapper<>(MaquinaUltrassomEspec.class));

        if (maquinaUltraEspec.isEmpty()) {

            con.execute(String.format("""
                      insert into maquina_ultrassom_especificada 
                          (uso_maximo, fk_maquina, fk_especificacao_componente)
                      values (%d, %d, %d);
                    """, usoMaximotoInt, fkMaquina, fkEspecComp));

            maquinaUltraEspec
                    = con.query(String.format("""
                                select
                                    m.*
                                from 
                                    maquina_ultrassom_especificada m
                                join 
                                    especificacao_componente e
                                on
                                    m.fk_especificacao_componente = e.id_especificacao_componente
                                where 
                                    uso_maximo = %d
                                and fk_maquina = %d
                                and fk_especificacao_componente = %d                
                            """, usoMaximotoInt, fkMaquina, fkEspecComp),
                    new BeanPropertyRowMapper<>(MaquinaUltrassomEspec.class));
        }

        MaquinaUltrassomEspec dados = maquinaUltraEspec.get(0);

        if (maquinaUltraEspecLocal.isEmpty()) {

            conMysql.execute(String.format("""
                      insert into maquina_ultrassom_especificada 
                          (id_especificacao_componente_maquina,uso_maximo, fk_maquina, fk_especificacao_componente)
                      values (%d,%d, %d, %d);
                    """, dados.getIdEspecificacaoComponenteMaquina(), usoMaximotoInt, fkMaquina, fkEspecComp));
        }


        maquinasUltrassomEspecs.add(new MaquinaUltrassomEspec(dados.getIdEspecificacaoComponenteMaquina(),
                dados.getUsoMaximo(),
                dados.getFkMaquina(),
                dados.getFkEspecificacaoComponente()));
    }

    public Boolean verifyMachineAutorization() {
        String idProcessador = new Looca().getProcessador().getId();

        List<MaquinaUltrassom> maquinasUltra = con.query(String.format("""
                        select 
                            m.* 
                        from 
                            maquina_ultrassom as m
                        where 
                            m.numero_serial_maquina = '%s';
                        """, idProcessador),
                new BeanPropertyRowMapper(MaquinaUltrassom.class));

        List<MaquinaUltrassom> maquinasUltraLocal = conMysql.query(String.format("""
                        select 
                            m.* 
                        from 
                            maquina_ultrassom as m
                        where 
                            m.numero_serial_maquina = '%s';
                        """, idProcessador),
                new BeanPropertyRowMapper(MaquinaUltrassom.class));

        MaquinaUltrassom dados = maquinasUltra.get(0);
        MaquinaUltrassom dadosLocal = maquinasUltraLocal.get(0);

        if (!dadosLocal.getStatusMaquina().equalsIgnoreCase(dados.getStatusMaquina())) {
            conMysql.execute(String.format("""
                        UPDATE maquina_ultrassom
                        SET
                            status_maquina = '%s'
                        WHERE
                            id_maquina = %d AND fk_administrador = %d
                                AND fk_empresa = %d;
                    """, dados.getStatusMaquina(), dados.getIdMaquina(), dados.getFkAdministrador(), dados.getFkEmpresa()));
        }

        return dados.getStatusMaquina().equalsIgnoreCase("true");
    }

    public MetricaComponente setMetrica(MetricaComponente metrica) {
        String dataAtual = metrica.getDateFormatedSql();
        con.update("insert into metrica_componente (dt_metrica,uso,fk_especificacao_componente_maquina) values (?,?,?)",
                dataAtual, metrica.getUsoComponente(), metrica.getFkEspecificacaoComponente());

        List<MetricaComponente> metricas = con.query(String.format("""
                        select 
                            *
                        from
                            metrica_componente
                        where
                             dt_metrica = '%s'
                        and 
                            fk_especificacao_componente_maquina = %d
                        """, dataAtual, metrica.getFkEspecificacaoComponente()),
                new BeanPropertyRowMapper(MetricaComponente.class));

        MetricaComponente dados = metricas.get(0);

        System.out.println(String.format("""
                ==================================
                Metrica componente - Atual
                
                Id: %d
                Data alerta: %s
                Uso atual: %.2f
                Fk componente: %s
                ==================================
                
                """,dados.getIdMetricaComponente(),dataAtual,metrica.getUsoComponente(),metrica.getFkEspecificacaoComponente()));

        conMysql.update("insert into metrica_componente (id_metrica_componente,dt_metrica,uso,fk_especificacao_componente_maquina) values (?,?,?,?)",
                dados.getIdMetricaComponente(), dataAtual, dados.getUsoComponente(), dados.getFkEspecificacaoComponente());

        return dados;
    }

    public void setAlerta(Alerta alerta) {
        con.execute(String.format("""
                insert into alerta (dt_alerta,fk_tipo_alerta,fk_metrica_componente) values ('%s',%d,%d)
                """, alerta.getDateFormatedSql(), alerta.getTipoAlerta(), alerta.getFkMetricaComponente()));
        conMysql.execute(String.format("""
                insert into alerta (dt_alerta,fk_tipo_alerta,fk_metrica_componente) values ('%s',%d,%d)
                """, alerta.getDateFormatedSql(), alerta.getTipoAlerta(), alerta.getFkMetricaComponente()));
    }

    public String getLink() {
        List<Webhook> links = con.query("SELECT link FROM webhook WHERE id_web = 3",
                new BeanPropertyRowMapper(Webhook.class));

        return links.get(0).getLink();
    }

    public void updateStatusConexao(String statusMaquina, Integer idMaquina) {
        con.execute(String.format("""
                update maquina_ultrassom set status_conexao = '%s' where id_maquina = %d;
                """, statusMaquina, idMaquina));

        conMysql.execute(String.format("""
                update maquina_ultrassom set status_conexao = '%s' where id_maquina = %d;
                """, statusMaquina, idMaquina));
    }

    public Double convertBytesToGB(long bytes) {
        return bytes / (1024.0 * 1024.0 * 1024.0);
    }

    public Double convertBytesToMB(long bytes) {
        return bytes / (1024.0 * 1024.0);
    }

    public MaquinaUltrassom getMaquinaUltrassom() {
        return maquinaUltrassom;
    }

    public List<EspecificacaoComponente> getEspecificacoesComponente() {
        return especificacoesComponente;
    }

    public List<MaquinaUltrassomEspec> getMaquinasUltrassomEspecs() {
        return maquinasUltrassomEspecs;
    }

    public List<RedeInterface> getRedeAtual() {
        return redeAtual;
    }

}
