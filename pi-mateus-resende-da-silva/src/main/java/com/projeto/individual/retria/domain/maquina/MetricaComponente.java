package com.projeto.individual.retria.domain.maquina;

import java.time.LocalDateTime;

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
                Metrica componente
                
                Id: %d
                Data alerta: %s
                Uso maximo: %.2f
                Fk componente: %s
                ==================================
                
                """,idMetricaComponente,dtMetrica,usoComponente,fkEspecificacaoComponente);
    }
}
