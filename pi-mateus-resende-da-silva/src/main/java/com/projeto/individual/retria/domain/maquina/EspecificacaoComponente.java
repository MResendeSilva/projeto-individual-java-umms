package com.projeto.individual.retria.domain.maquina;

import com.projeto.individual.retria.infra.componentes.TipoComponente;

public class EspecificacaoComponente {
    private Integer idEspecComponente;
    private TipoComponente tipoComponente;
    private String nomeFabricante;
    private String descricaoComponente;
    private String numeroSerial;

    public EspecificacaoComponente(Integer idEspecComponente, TipoComponente tipoComponente, String nomeFabricante,
                                   String descricaoComponente, String numeroSerial) {
        this.idEspecComponente = idEspecComponente;
        this.tipoComponente = tipoComponente;
        this.nomeFabricante = nomeFabricante;
        this.descricaoComponente = descricaoComponente;
        this.numeroSerial = numeroSerial;
    }

    public EspecificacaoComponente() {
    }

    public Integer getIdEspecComponente() {
        return idEspecComponente;
    }

    public void setIdEspecComponente(Integer idEspecComponente) {
        this.idEspecComponente = idEspecComponente;
    }

    public TipoComponente getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(TipoComponente tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    public String getNomeFabricante() {
        return nomeFabricante;
    }

    public void setNomeFabricante(String nomeFabricante) {
        this.nomeFabricante = nomeFabricante;
    }

    public String getDescricaoComponente() {
        return descricaoComponente;
    }

    public void setDescricaoComponente(String descricaoComponente) {
        this.descricaoComponente = descricaoComponente;
    }

    public String getNumeroSerial() {
        return numeroSerial;
    }

    public void setNumeroSerial(String numeroSerial) {
        this.numeroSerial = numeroSerial;
    }

    @Override
    public String toString() {
        return String.format("""
                ==================================
                Componente
                
                Id: %d
                Tipo: %s
                Fabricante: %s
                Descricao: %s
                Numero serial: %s
                ==================================
                
                """,idEspecComponente,tipoComponente,nomeFabricante,descricaoComponente,numeroSerial);
    }
}
