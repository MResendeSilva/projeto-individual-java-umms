package com.projeto.individual.retria.domain.maquina;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MetricaComponente {
    private Integer idMetricaComponente;

    private final LocalDateTime dtMetrica = LocalDateTime.now();

    private Double usoComponente;

    private Integer fkEspecificacaoComponente;

    public MetricaComponente(Integer idMetricaComponente, Double usoComponente, Integer fkEspecificacaoComponente) {
        this.idMetricaComponente = idMetricaComponente;
        this.usoComponente = usoComponente;
        this.fkEspecificacaoComponente = fkEspecificacaoComponente;
    }

    public MetricaComponente() {
    }

    public String getDateFormatedSql () {
        return dtMetrica.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Integer getIdMetricaComponente() {
        return idMetricaComponente;
    }

    public void setIdMetricaComponente(Integer idMetricaComponente) {
        this.idMetricaComponente = idMetricaComponente;
    }

    public LocalDateTime getDtMetrica() {
        return dtMetrica;
    }

    public Double getUsoComponente() {
        return usoComponente;
    }

    public void setUsoComponente(Double usoComponente) {
        this.usoComponente = usoComponente;
    }

    public Integer getFkEspecificacaoComponente() {
        return fkEspecificacaoComponente;
    }

    public void setFkEspecificacaoComponente(Integer fkEspecificacaoComponente) {
        this.fkEspecificacaoComponente = fkEspecificacaoComponente;
    }



    @Override
    public String toString() {
        return String.format("""
                ==================================
                Metrica componente - Atual
                
                Id: %d
                Data alerta: %s
                Uso atual: %.2f
                Fk componente: %s
                ==================================
                
                """,idMetricaComponente,dtMetrica,usoComponente,fkEspecificacaoComponente);
    }
}
