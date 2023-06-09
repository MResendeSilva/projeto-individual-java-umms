package com.projeto.individual.retria.domain.maquina;

public class MaquinaUltrassomEspec {
    private Integer idEspecificacaoComponenteMaquina;
    private Double usoMaximo;
    private Integer fkMaquina;
    private Integer fkEspecificacaoComponente;

    public MaquinaUltrassomEspec(Integer idEspecificacaoComponenteMaquina, Double usoMaximo, Integer fkMaquina, Integer fkEspecificacaoComponente) {
        this.idEspecificacaoComponenteMaquina = idEspecificacaoComponenteMaquina;
        this.usoMaximo = usoMaximo;
        this.fkMaquina = fkMaquina;
        this.fkEspecificacaoComponente = fkEspecificacaoComponente;
    }

    public MaquinaUltrassomEspec() {
    }

    public Integer getIdEspecificacaoComponenteMaquina() {
        return idEspecificacaoComponenteMaquina;
    }

    public void setIdEspecificacaoComponenteMaquina(Integer idEspecificacaoComponenteMaquina) {
        this.idEspecificacaoComponenteMaquina = idEspecificacaoComponenteMaquina;
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
                Maquina com componente especificado
                
                Id: %d
                Uso maximo: %s
                Fk maquina: %d
                Fk componente: %s
                ==================================
                
                """, idEspecificacaoComponenteMaquina,usoMaximo,fkMaquina, fkEspecificacaoComponente);
    }
}
