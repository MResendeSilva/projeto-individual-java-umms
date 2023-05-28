package com.projeto.individual.retria.domain.maquina;

public class Empresa {
    private Integer idEmpresa;
    private String nomeEmpresa;
    private String cnpj;
    private String telefone01;
    private String telefone02;
    private String email;
    private String responsavelEmpresa;
    private Integer fkMatriz;

    public Empresa(Integer idEmpresa, String nomeEmpresa, String cnpj, String telefone01, String telefone02,
                   String email, String responsavelEmpresa, Integer fkMatriz) {
        this.idEmpresa = idEmpresa;
        this.nomeEmpresa = nomeEmpresa;
        this.cnpj = cnpj;
        this.telefone01 = telefone01;
        this.telefone02 = telefone02;
        this.email = email;
        this.responsavelEmpresa = responsavelEmpresa;
        this.fkMatriz = fkMatriz;
    }

    public Empresa() {
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone01() {
        return telefone01;
    }

    public void setTelefone01(String telefone01) {
        this.telefone01 = telefone01;
    }

    public String getTelefone02() {
        return telefone02;
    }

    public void setTelefone02(String telefone02) {
        this.telefone02 = telefone02;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResponsavelEmpresa() {
        return responsavelEmpresa;
    }

    public void setResponsavelEmpresa(String responsavelEmpresa) {
        this.responsavelEmpresa = responsavelEmpresa;
    }

    public Integer getFkMatriz() {
        return fkMatriz;
    }

    public void setFkMatriz(Integer fkMatriz) {
        this.fkMatriz = fkMatriz;
    }

    @Override
    public String toString() {
        return String.format("""
                ==================================
                Empresa
                
                Id: %d
                Nome: %s
                Cnpj: %s
                Responsavel: %s
                Fk matriz : %d
                ==================================
                
                """,idEmpresa,nomeEmpresa,cnpj,responsavelEmpresa,fkMatriz);
    }
}
