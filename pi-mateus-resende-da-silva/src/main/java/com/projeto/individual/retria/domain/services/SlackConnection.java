package com.projeto.individual.retria.domain.services;

import com.github.britooo.looca.api.core.Looca;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.projeto.individual.retria.repository.MaquinaRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SlackConnection {
    private static final String slackChannel = "hospital-estadual-de-vila-alpina-";
    private LocalDateTime teste = LocalDateTime.now();

    public void sendMensagemToSlack(String mensagem) {
        String webHooksUrl = new MaquinaRepository().getLink();

        LocalDateTime teste2 = LocalDateTime.now();

        if (ChronoUnit.MINUTES.between(teste, teste2) >= 1) {
            try {
                System.out.println("ESTOU NA CLASSE DO SLACK");
                StringBuilder msgbuilde = new StringBuilder();
                msgbuilde.append(mensagem);
                Payload payload = Payload.builder().channel(slackChannel).text(msgbuilde.toString()).build();
                com.github.seratch.jslack.api.webhook.WebhookResponse wbResp = Slack.getInstance().send(webHooksUrl, payload);
                System.out.println("#####ALERTA ENVIADO PARA O SLACK#####");
                teste = LocalDateTime.now();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("#####AGUARDE 1 MINUTO PARA O PROXIMO ALERTA#####");
        }
    }

}
