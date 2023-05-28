package com.projeto.individual.retria.domain.maquina;

public class Administrador {

    private Integer idAdministrador;
    private String nomeAdministrador;
    private String emailAdministrador;

    private String senhaAdministrador;
    private String telefoneAdministrador;
    private String chaveSegurancaAdministrador;
    private Integer fkOcupacao;
    private Integer fkEmpresa;

    public Administrador(Integer idAdministrador, String nomeAdministrador, String emailAdministrador,
                         String senhaAdministrador, String telefoneAdministrador,
                         String chaveSegurancaAdministrador, Integer fkOcupacao, Integer fkEmpresa) {

        this.idAdministrador = idAdministrador;
        this.nomeAdministrador = nomeAdministrador;
        this.emailAdministrador = emailAdministrador;
        this.senhaAdministrador = senhaAdministrador;
        this.telefoneAdministrador = telefoneAdministrador;
        this.chaveSegurancaAdministrador = chaveSegurancaAdministrador;
        this.fkOcupacao = fkOcupacao;
        this.fkEmpresa = fkEmpresa;
    }

    public Administrador() {
    }

    public Integer getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(Integer idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getNomeAdministrador() {
        return nomeAdministrador;
    }

    public void setNomeAdministrador(String nomeAdministrador) {
        this.nomeAdministrador = nomeAdministrador;
    }

    public String getEmailAdministrador() {
        return emailAdministrador;
    }

    public void setEmailAdministrador(String emailAdministrador) {
        this.emailAdministrador = emailAdministrador;
    }

    public String getSenhaAdministrador() {
        return senhaAdministrador;
    }

    public void setSenhaAdministrador(String senhaAdministrador) {
        this.senhaAdministrador = senhaAdministrador;
    }

    public String getTelefoneAdministrador() {
        return telefoneAdministrador;
    }

    public void setTelefoneAdministrador(String telefoneAdministrador) {
        this.telefoneAdministrador = telefoneAdministrador;
    }

    public String getChaveSegurancaAdministrador() {
        return chaveSegurancaAdministrador;
    }

    public void setChaveSegurancaAdministrador(String chaveSegurancaAdministrador) {
        this.chaveSegurancaAdministrador = chaveSegurancaAdministrador;
    }

    public Integer getFkOcupacao() {
        return fkOcupacao;
    }

    public void setFkOcupacao(Integer fkOcupacao) {
        this.fkOcupacao = fkOcupacao;
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
                Administrador
                
                Id: %d
                Nome: %s
                Email: %s
                Fk ocupacao: %d
                Fk empresa: %d
                ==================================
                
                """,idAdministrador,nomeAdministrador,emailAdministrador,fkOcupacao,fkEmpresa);
    }
}
