package com.projeto.individual.retria.domain.services;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import com.projeto.individual.retria.domain.maquina.Alerta;
import com.projeto.individual.retria.domain.maquina.MetricaComponente;
import com.projeto.individual.retria.infra.exception.ValidacaoException;
import com.projeto.individual.retria.repository.MaquinaRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ValidadorComponente {
    private MaquinaRepository maquinaRepository = new MaquinaRepository();
    private Monitoramento service = new Monitoramento();
    private Looca looca = new Looca();
    private SlackConnection slack = new SlackConnection();
    public void validarCpu(Processador dados, Integer fkMaquinaUltrassom) throws ValidacaoException {
        Double usoProcessador = dados.getUso();
        MetricaComponente metricaComponente = new MetricaComponente(null, usoProcessador, fkMaquinaUltrassom);
        metricaComponente = maquinaRepository.setMetrica(metricaComponente);
        Integer fkMetricaComponente = metricaComponente.getIdMetricaComponente();

        String frase = "";

        if (dados == null) {
            throw new ValidacaoException("Não é possível validar o uso de cpu nulo!!!");
        }

        if (usoProcessador < 35.0) {
            System.out.println("Uso dentro dos conformes!");
        } else if (usoProcessador < 40.0) {
            maquinaRepository.setAlerta(new Alerta(null, 1, fkMetricaComponente));
            frase = "Status maquina " + fkMaquinaUltrassom + " <====> " + "CPU está com nível de uso em - ALERTA!";
            slack.sendMensagemToSlack(frase);
            throw new ValidacaoException(frase);
        } else if (usoProcessador < 45.0) {
            maquinaRepository.setAlerta(new Alerta(null, 2, fkMetricaComponente));
            frase = "Status maquina " + fkMaquinaUltrassom + " <====> " + "CPU está com nível de uso em - PERIGOSO! Contate o suporte!";
            slack.sendMensagemToSlack(frase);
            throw new ValidacaoException(frase);
        } else {
            maquinaRepository.setAlerta(new Alerta(null, 3, fkMetricaComponente));
            frase = "Status maquina " + fkMaquinaUltrassom + " <====> " + "CPU está com nível de uso em - CRÍTICO! Contate o suporte IMEDIATAMENTE!";
            slack.sendMensagemToSlack(frase);
            throw new ValidacaoException(frase);
        }
    }

    public void validarRam(Memoria dados, Integer fkMaquinaUltrassom) throws ValidacaoException {
        Double memRamTotal = service.convertBytesToGB(dados.getTotal());
        Double usoMemoria = service.convertBytesToGB(dados.getEmUso());
        Double porcentagemDeRam = (usoMemoria * 100) / memRamTotal;

        System.out.println("USO DE RAM " + usoMemoria);
        MetricaComponente metricaComponente = new MetricaComponente(null, porcentagemDeRam, fkMaquinaUltrassom);
        metricaComponente = maquinaRepository.setMetrica(metricaComponente);
        Integer fkMetricaComponente = metricaComponente.getIdMetricaComponente();

        String frase = "";

        if (dados == null) {
            throw new ValidacaoException("Não é possível validar memória nula!!!");
        }
        if (porcentagemDeRam < 49.0) {
            System.out.println("Uso de ram dentro dos conformes!");
        } else if (porcentagemDeRam < 56.0) {
            maquinaRepository.setAlerta(new Alerta(null, 1, fkMetricaComponente));
            frase = "Status maquina " + fkMaquinaUltrassom + " <====> " + "RAM está com nível de uso em - ALERTA!";
            slack.sendMensagemToSlack(frase);
            throw new ValidacaoException(frase);
        } else if (porcentagemDeRam < 63.0) {
            maquinaRepository.setAlerta(new Alerta(null, 2, fkMetricaComponente));
            frase = "Status maquina " + fkMaquinaUltrassom + " <====> " + "RAM está com nível de uso em - PERIGOSO! Contate o suporte!";
            slack.sendMensagemToSlack(frase);
            throw new ValidacaoException(frase);
        } else {
            maquinaRepository.setAlerta(new Alerta(null, 3, fkMetricaComponente));
            frase = "Status maquina " + fkMaquinaUltrassom + " <====> " + "RAM está com nível de uso em - CRÍTICO! Contate o suporte IMEDIATAMENTE!";
            slack.sendMensagemToSlack(frase);
            throw new ValidacaoException(frase);
        }
    }

    public void validarDisco(Volume dados, Integer fkMaquinaUltrassom) throws ValidacaoException {
        Double emUso = service.convertBytesToGB(dados.getTotal() - dados.getDisponivel());
        Double porcentagemDeUsoDisc = (emUso * 100) / service.convertBytesToGB(dados.getTotal());
        System.out.println("---------------------------- " + porcentagemDeUsoDisc);
        MetricaComponente metricaComponente = new MetricaComponente(null, porcentagemDeUsoDisc, fkMaquinaUltrassom);
        metricaComponente = maquinaRepository.setMetrica(metricaComponente);
        Integer fkMetricaComponente = metricaComponente.getIdMetricaComponente();

        String frase = "";

        if (dados == null) {
            throw new ValidacaoException("Não é possível validar discos de uma lista vazia!!!");
        }

        if (porcentagemDeUsoDisc < 56.0) {
            System.out.println("Uso de DISCO dentro dos conformes!");
        } else if (porcentagemDeUsoDisc < 64.0) {
            maquinaRepository.setAlerta(new Alerta(null, 1, fkMetricaComponente));
            frase = "Status maquina " + fkMaquinaUltrassom + " <====> " + "DISCO está com nível de uso em - ALERTA!";
            slack.sendMensagemToSlack(frase);
            throw new ValidacaoException(frase);
        } else if (porcentagemDeUsoDisc < 72.0) {
            maquinaRepository.setAlerta(new Alerta(null, 2, fkMetricaComponente));
            frase = "Status maquina " + fkMaquinaUltrassom + " <====> " + "DISCO está com nível de uso em - PERIGOSO! Contate o suporte!";
            slack.sendMensagemToSlack(frase);
            throw new ValidacaoException(frase);
        } else {
            maquinaRepository.setAlerta(new Alerta(null, 3, fkMetricaComponente));
            frase = "Status maquina " + fkMaquinaUltrassom + " <====> " + "DISCO está com nível de uso em - CRÍTICO! Contate o suporte IMEDIATAMENTE!";
            slack.sendMensagemToSlack(frase);
            throw new ValidacaoException(frase);
        }
    }

    public void validarRede(Integer fkRede) {
        try {
            List<RedeInterface> interfaces = looca.getRede().getGrupoDeInterfaces().getInterfaces();
            RedeInterface redeAtual = interfaces.get(0);

            for (int i = 0; i < interfaces.size(); i++) {
                RedeInterface redeUsada = interfaces.get(i);

                if (redeUsada.getBytesRecebidos() > redeAtual.getBytesRecebidos()) {
                    redeAtual = redeUsada;
                }
            }

            long bytesRec1 = redeAtual.getBytesRecebidos();
            TimeUnit.SECONDS.sleep(3);
            long bytesRec2 = redeAtual.getBytesRecebidos();
            Double mbpsAtual = service.convertBytesToMB(bytesRec2 - bytesRec1);
            System.out.println(mbpsAtual);
            MetricaComponente metricaComponente = new MetricaComponente(null, mbpsAtual, fkRede);
            metricaComponente = maquinaRepository.setMetrica(metricaComponente);
            Integer fkMetricaComponente = metricaComponente.getIdMetricaComponente();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
