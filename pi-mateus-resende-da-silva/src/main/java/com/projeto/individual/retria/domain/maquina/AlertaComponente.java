package com.projeto.individual.retria.domain.maquina;

import java.time.LocalDateTime;

public class AlertaComponente {
    private Integer idAlerta;
    private final LocalDateTime dtMetrica = LocalDateTime.now();
    private Integer tipoAlerta;
    private Integer fkMetricaComponente;

    public AlertaComponente(Integer idAlerta, Integer tipoAlerta, Integer fkMetricaComponente) {
        this.idAlerta = idAlerta;
        this.tipoAlerta = tipoAlerta;
        this.fkMetricaComponente = fkMetricaComponente;
    }

    public AlertaComponente() {
    }

    public Integer getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Integer idAlerta) {
        this.idAlerta = idAlerta;
    }

    public LocalDateTime getDtMetrica() {
        return dtMetrica;
    }

    public Integer getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(Integer tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public Integer getFkMetricaComponente() {
        return fkMetricaComponente;
    }

    public void setFkMetricaComponente(Integer fkMetricaComponente) {
        this.fkMetricaComponente = fkMetricaComponente;
    }

    @Override
    public String toString() {
        return String.format("""
                ==================================
                Alerta do componente
                
                Id: %d
                Data alerta %s
                Tipo alerta: %d
                Fk metrica: %d
                ==================================
                
                """,idAlerta,dtMetrica,tipoAlerta,fkMetricaComponente);
    }
}
