package com.projeto.individual.retria.repository;

import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import com.projeto.individual.retria.domain.maquina.EspecificacaoComponente;
import com.projeto.individual.retria.domain.maquina.MaquinaUltrassom;
import com.projeto.individual.retria.domain.maquina.MaquinaUltrassomEspec;
import com.projeto.individual.retria.domain.services.Monitoramento;
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
    private Monitoramento monitoramento = new Monitoramento();

    JdbcTemplate con;
    JdbcTemplate conMysql;

    public MaquinaRepository() {
        ConexaoSql conexao = new ConexaoSql();
        con = conexao.getConnection();
        ConexaoMySql conexaoMysql = new ConexaoMySql();
        conMysql = conexaoMysql.getConnection();
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

        if (especificacaoComponentesLocal.isEmpty()) {

            conMysql.execute(String.format("insert into especificacao_componente" +
                            "(id_especificacao_componente,tipo_componente, descricao_componente, nome_fabricante, numero_serial) " +
                            "values (%d,'%s', '%s', '%s', '%s')",
                    dados.getIdEspecComponente(), "CPU", processador.getNome(), processador.getFabricante(), processador.getId()));
        }

        especificacoesComponente.add(new EspecificacaoComponente(dados.getIdEspecComponente(),
                dados.getTipoComponente(), dados.getNomeFabricante(), dados.getDescricaoComponente(), dados.getNumeroSerial()));
    }

    public void setComponente(Memoria memoria) {
        String nomeMemoria = String.format("Pente de memória ram - %.0f GB",
                monitoramento.convertBytesToGB(memoria.getTotal()));

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

        if (especificacaoComponentesLocal.size() == 0) {

            conMysql.execute(String.format("insert into especificacao_componente" +
                            "(id_especificacao_componente,tipo_componente, descricao_componente) values (%d,'%s','%s')",
                    dados.getIdEspecComponente(), "RAM", nomeMemoria));
        }

        especificacoesComponente.add(new EspecificacaoComponente(dados.getIdEspecComponente(),
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
                        """, redeAtual.getNomeExibicao(),redeAtual.getEnderecoMac()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

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
                        """, redeAtual.getNomeExibicao(),redeAtual.getEnderecoMac()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        if (especificacaoComponentes.isEmpty()) {
            con.execute(String.format("insert into especificacao_componente" +
                            "(tipo_componente,descricao_componente, numero_serial) values ('%s', '%s','%s')",
                    "REDE",redeAtual.getNomeExibicao(), redeAtual.getEnderecoMac()));

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
                            """, redeAtual.getNomeExibicao(),redeAtual.getEnderecoMac()), new BeanPropertyRowMapper<>(EspecificacaoComponente.class));

        }
        EspecificacaoComponente dados = especificacaoComponentes.get(0);
        if (especificacaoComponentesLocal.isEmpty()) {
            conMysql.execute(String.format("insert into especificacao_componente" +
                            "(id_especificacao_componente,tipo_componente,descricao_componente, numero_serial) values (%d,'%s', '%s','%s')",
                    dados.getIdEspecComponente(),"REDE",redeAtual.getNomeExibicao(),redeAtual.getEnderecoMac()));
        }

        especificacoesComponente.add(new EspecificacaoComponente(dados.getIdEspecComponente(),
                dados.getTipoComponente(), dados.getNomeFabricante(), dados.getDescricaoComponente(),dados.getNumeroSerial()));
    }

    public void setComponente(Volume disco) {
        if (monitoramento.convertBytesToGB(disco.getTotal()) >= 1) {

            EspecificacaoComponente discoCadastrado = especificacoesComponente.stream().
                    filter(e -> e.getNumeroSerial().equalsIgnoreCase(disco.getUUID()) && e.getTipoComponente()
                            .equals(TipoComponente.DISCO)).findFirst().get();

            if (!disco.getUUID().equalsIgnoreCase(discoCadastrado.getNumeroSerial())) {

                String nomeDisco = String.format("HD/SSD - %.0f GB", monitoramento.convertBytesToGB(disco.getTotal()));
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
                            dados.getIdEspecComponente(), "DISCO", nomeDisco, disco.getUUID()));
                }

                especificacoesComponente.add(new EspecificacaoComponente(dados.getIdEspecComponente(),
                        dados.getTipoComponente(), dados.getNomeFabricante(), dados.getDescricaoComponente(), dados.getNumeroSerial()));
            }
        }
    }

    public void setMaquinaUltrassomEspec() {
        public MaquinaUltrassomEspecificada getMaquiUltassomEspecCPU(Double usoMaximo, Integer fkMaquina, Integer fkEspecComp) {
            Integer usoMaximotoInt = usoMaximo.intValue();
            List<MaquinaUltrassomEspecificada> maquinaUltraEspec = con.query(String.format("""
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
                        and e.tipo_componente = 'CPU';
                    """, usoMaximotoInt, fkMaquina, fkEspecComp),
                    new BeanPropertyRowMapper<>(MaquinaUltrassomEspecificada.class));

            List<MaquinaUltrassomEspecificada> maquinaUltraEspecLocal = conMysql.query(String.format("""
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
                        and e.tipo_componente = 'CPU';
                    """, usoMaximotoInt, fkMaquina, fkEspecComp),
                    new BeanPropertyRowMapper<>(MaquinaUltrassomEspecificada.class));

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
                        and e.tipo_componente = 'CPU';
                    """, usoMaximotoInt,fkMaquina, fkEspecComp),
                        new BeanPropertyRowMapper<>(MaquinaUltrassomEspecificada.class));
            }

            if (maquinaUltraEspecLocal.isEmpty()) {
                MaquinaUltrassomEspecificada dados = maquinaUltraEspec.get(0);

                conMysql.execute(String.format("""
                        insert into maquina_ultrassom_especificada 
                            (id_especificacao_componente_maquina,uso_maximo, fk_maquina, fk_especificacao_componente)
                        values (%d,%d, %d, %d);
                      """,dados.getId_especificacao_componente_maquina(),usoMaximotoInt, fkMaquina, fkEspecComp));
            }

            MaquinaUltrassomEspecificada dados = maquinaUltraEspec.get(0);

            System.out.println("ESPEFIFICAÇÃO DE COMPONENTES CADASTRADOS COM SUCESSO!");
            return new MaquinaUltrassomEspecificada(dados.getId_especificacao_componente_maquina(),
                    dados.getUso_maximo(),
                    dados.getFk_maquina(),
                    dados.getFk_especificacao_componente());
        }
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

    public Monitoramento getMonitoramento() {
        return monitoramento;
    }
}
