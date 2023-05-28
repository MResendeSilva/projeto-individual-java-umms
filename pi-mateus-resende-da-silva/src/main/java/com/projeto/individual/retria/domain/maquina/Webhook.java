package com.projeto.individual.retria.domain.maquina;

public class Webhook {
    private Integer idWeb;
    private String link;

    public Webhook(Integer idWeb, String link) {
        this.idWeb = idWeb;
        this.link = link;
    }

    public Webhook() {
    }

    public Integer getIdWeb() {
        return idWeb;
    }

    public void setIdWeb(Integer idWeb) {
        this.idWeb = idWeb;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return String.format("""
                ==================================
                Webhook
                
                Id: %d
                Link: %s
                ==================================
                
                """,idWeb,link);
    }
}
