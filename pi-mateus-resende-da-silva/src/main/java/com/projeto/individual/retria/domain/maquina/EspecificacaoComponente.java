package com.projeto.individual.retria.domain.maquina;

import com.projeto.individual.retria.infra.componentes.TipoComponente;

public class EspecificacaoComponente {
    private Integer idEspecificacaoComponente;
    private TipoComponente tipoComponente;
    private String nomeFabricante;
    private String descricaoComponente;
    private String numeroSerial;

    public EspecificacaoComponente(Integer idEspecificacaoComponente, TipoComponente tipoComponente, String nomeFabricante,
                                   String descricaoComponente, String numeroSerial) {
        this.idEspecificacaoComponente = idEspecificacaoComponente;
        this.tipoComponente = tipoComponente;
        this.nomeFabricante = nomeFabricante;
        this.descricaoComponente = descricaoComponente;
        this.numeroSerial = numeroSerial;
    }

    public EspecificacaoComponente() {
    }

    public Integer getIdEspecificacaoComponente() {
        return idEspecificacaoComponente;
    }

    public void setIdEspecificacaoComponente(Integer idEspecificacaoComponente) {
        this.idEspecificacaoComponente = idEspecificacaoComponente;
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
                
                """, idEspecificacaoComponente,tipoComponente,nomeFabricante,descricaoComponente,numeroSerial);
    }
}
