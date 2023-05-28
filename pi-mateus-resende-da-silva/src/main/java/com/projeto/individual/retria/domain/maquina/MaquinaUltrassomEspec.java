package com.projeto.individual.retria.domain.maquina;

public class MaquinaUltrassomEspec {
    private Integer idEspecMaquina;
    private Double usoMaximo;
    private Integer fkMaquina;
    private Integer fkEspecComponente;

    public MaquinaUltrassomEspec(Integer idEspecMaquina, Double usoMaximo, Integer fkMaquina, Integer fkEspecComponente) {
        this.idEspecMaquina = idEspecMaquina;
        this.usoMaximo = usoMaximo;
        this.fkMaquina = fkMaquina;
        this.fkEspecComponente = fkEspecComponente;
    }

    public MaquinaUltrassomEspec() {
    }

    public Integer getIdEspecMaquina() {
        return idEspecMaquina;
    }

    public void setIdEspecMaquina(Integer idEspecMaquina) {
        this.idEspecMaquina = idEspecMaquina;
    }

    public Double getUsoMaximo() {
        return usoMaximo;
    }

    public void setUsoMaximo(Double usoMaximo) {
        this.usoMaximo = usoMaximo;
    }

    public Integer getFkMaquina() {
        return fkMaquina;
    }

    public void setFkMaquina(Integer fkMaquina) {
        this.fkMaquina = fkMaquina;
    }

    public Integer getFkEspecComponente() {
        return fkEspecComponente;
    }

    public void setFkEspecComponente(Integer fkEspecComponente) {
        this.fkEspecComponente = fkEspecComponente;
    }

    @Override
    public String toString() {
        return String.format("""
                ==================================
                Maquina com componente especificado
                
                Id: %d
                Uso maximo: %s
                Fk maquina: %d
                Fk componente: %s
                ==================================
                
                """,idEspecMaquina,usoMaximo,fkMaquina,fkEspecComponente);
    }
}
