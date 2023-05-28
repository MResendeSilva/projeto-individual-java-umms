package com.projeto.individual.retria.domain.maquina;

public class MaquinaUltrassom {
    private Integer idMaquina;
    private String sistemaOperacional;
    private String numeroSerialMaquina;
    private String statusMaquina;
    private String statusConexao;
    private Integer fkAdministrador;
    private Integer fkEmpresa;

    public MaquinaUltrassom(Integer idMaquina, String sistemaOperacional, String numeroSerialMaquina,
                            String statusMaquina, String status_conexao, Integer fkAdministrador, Integer fkEmpresa) {

        this.idMaquina = idMaquina;
        this.sistemaOperacional = sistemaOperacional;
        this.numeroSerialMaquina = numeroSerialMaquina;
        this.statusMaquina = statusMaquina;
        this.statusConexao = status_conexao;
        this.fkAdministrador = fkAdministrador;
        this.fkEmpresa = fkEmpresa;
    }

    public MaquinaUltrassom() {
    }

    public Integer getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(Integer idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public String getNumeroSerialMaquina() {
        return numeroSerialMaquina;
    }

    public void setNumeroSerialMaquina(String numeroSerialMaquina) {
        this.numeroSerialMaquina = numeroSerialMaquina;
    }

    public String getStatusMaquina() {
        return statusMaquina;
    }

    public void setStatusMaquina(String statusMaquina) {
        this.statusMaquina = statusMaquina;
    }

    public String getStatusConexao() {
        return statusConexao;
    }

    public void setStatusConexao(String statusConexao) {
        this.statusConexao = statusConexao;
    }

    public Integer getFkAdministrador() {
        return fkAdministrador;
    }

    public void setFkAdministrador(Integer fkAdministrador) {
        this.fkAdministrador = fkAdministrador;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    @Override
    public String toString() {
        return String.format("""
                ==================================
                Maquina
                
                Id: %d
                Sistema operacional: %s
                Numero serial: %s
                Status da maquina: %s
                Status de conexão: %s
                Fk administrador: %d
                Fk empresa: %d
                ==================================
                
                """,idMaquina,sistemaOperacional,numeroSerialMaquina,statusMaquina,
                statusConexao,fkAdministrador,fkEmpresa);
    }
}
