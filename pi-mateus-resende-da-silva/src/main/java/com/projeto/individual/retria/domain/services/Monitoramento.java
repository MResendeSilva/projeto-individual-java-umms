package com.projeto.individual.retria.domain.services;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.seratch.jslack.api.model.User;
import com.projeto.individual.retria.domain.maquina.Administrador;
import com.projeto.individual.retria.domain.maquina.Empresa;
import com.projeto.individual.retria.domain.maquina.EspecificacaoComponente;
import com.projeto.individual.retria.infra.componentes.TipoComponente;
import com.projeto.individual.retria.repository.MaquinaRepository;
import com.projeto.individual.retria.repository.UserRepository;

import java.util.List;

public class Monitoramento {
    private final MaquinaRepository maquinaRepository = new MaquinaRepository();
    static Looca looca = new Looca();

    public void startMachineSettings(UserRepository userRepository) {
        System.out.println("Iniciando configuração da máquina e verificação de componentes...");
        Administrador adm = userRepository.getAdministrador();

        maquinaRepository.getMaquinaDeUltrassomById(adm.getIdAdministrador(), adm.getFkEmpresa());
        System.out.println("Máquina ok...");
        maquinaRepository.setComponente(looca.getProcessador());
        System.out.println("Processador ok...");
        maquinaRepository.setComponente(looca.getMemoria());
        System.out.println("Memória ok...");
        maquinaRepository.setComponente(looca.getRede().getGrupoDeInterfaces().getInterfaces());
        System.out.println("Interface de rede ok...");

        for (Volume disco : looca.getGrupoDeDiscos().getVolumes()) {
            if (convertBytesToGB(disco.getTotal()) >= 1) {
                maquinaRepository.setComponente(disco);
            }
        }
        System.out.println("Discos ok...");
        System.out.println("=============================================================================");
        startCompEspecification();
    }

    public void startCompEspecification() {
        System.out.println("Iniciando detalhamento de componentes...");
        List<EspecificacaoComponente> componentes = maquinaRepository.getEspecificacoesComponente();

        maquinaRepository.setMaquinaUltrassomEspec(100.0,
                maquinaRepository.getMaquinaUltrassom().getIdMaquina(), componentes.stream()
                        .filter(c -> c.getTipoComponente().equals(TipoComponente.CPU))
                        .findFirst().get().getIdEspecComponente());
        System.out.println("Detalhamento do processador ok...");

        maquinaRepository.setMaquinaUltrassomEspec(100.0,
                maquinaRepository.getMaquinaUltrassom().getIdMaquina(), componentes.stream()
                        .filter(c -> c.getTipoComponente().equals(TipoComponente.RAM))
                        .findFirst().get().getIdEspecComponente());
        System.out.println("Detalhamento da memória ok...");

        maquinaRepository.setMaquinaUltrassomEspec(100.0,
                maquinaRepository.getMaquinaUltrassom().getIdMaquina(), componentes.stream()
                        .filter(c -> c.getTipoComponente().equals(TipoComponente.REDE))
                        .findFirst().get().getIdEspecComponente());
        System.out.println("Detalhamento da rede ok...");

        for (int i = 0; i < componentes.size(); i++) {
            EspecificacaoComponente esAtual = componentes.get(i);

            if (esAtual.getTipoComponente().equals(TipoComponente.DISCO)) {
                maquinaRepository.setMaquinaUltrassomEspec(100.0,
                        maquinaRepository.getMaquinaUltrassom().getIdMaquina(), esAtual.getIdEspecComponente());
            }
        }
        System.out.println("Detalhamento de discos ok...");
        System.out.println("Configuração e detalhamento finalizados...");
        System.out.println("=============================================================================");
    }

    public Double convertBytesToGB(long bytes) {
        return bytes / (1024.0 * 1024.0 * 1024.0);
    }

    public Double convertBytesToMB(long bytes) {
        return bytes / (1024.0 * 1024.0);
    }
}
