package com.projeto.individual.retria.domain.services;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;
import com.projeto.individual.retria.domain.maquina.Administrador;
import com.projeto.individual.retria.domain.maquina.EspecificacaoComponente;
import com.projeto.individual.retria.domain.maquina.MaquinaUltrassomEspec;
import com.projeto.individual.retria.infra.componentes.TipoComponente;
import com.projeto.individual.retria.infra.exception.ValidacaoException;
import com.projeto.individual.retria.repository.MaquinaRepository;
import com.projeto.individual.retria.repository.UserRepository;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Monitoramento {
    MaquinaRepository maquinaRepository = new MaquinaRepository();
    UserRepository userRepository;
    static Looca looca = new Looca();

    public void startMachineSettings(UserRepository userRepository) {
        System.out.println("Iniciando configuração da máquina e verificação de componentes...");
        this.userRepository = userRepository;
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
        if (!maquinaRepository.verifyMachineAutorization()) {
            System.out.println("Maquina não está autorizada a operar!");
            startCompEspecification();
        } else {
            System.out.println("Iniciando detalhamento de componentes...");
            List<EspecificacaoComponente> componentes = maquinaRepository.getEspecificacoesComponente();

            maquinaRepository.setMaquinaUltrassomEspec(100.0,
                    maquinaRepository.getMaquinaUltrassom().getIdMaquina(), componentes.stream()
                            .filter(c -> c.getTipoComponente().equals(TipoComponente.CPU))
                            .findFirst().get().getIdEspecificacaoComponente());
            System.out.println("Detalhamento do processador ok...");

            maquinaRepository.setMaquinaUltrassomEspec(100.0,
                    maquinaRepository.getMaquinaUltrassom().getIdMaquina(), componentes.stream()
                            .filter(c -> c.getTipoComponente().equals(TipoComponente.RAM))
                            .findFirst().get().getIdEspecificacaoComponente());
            System.out.println("Detalhamento da memória ok...");

            maquinaRepository.setMaquinaUltrassomEspec(100.0,
                    maquinaRepository.getMaquinaUltrassom().getIdMaquina(), componentes.stream()
                            .filter(c -> c.getTipoComponente().equals(TipoComponente.REDE))
                            .findFirst().get().getIdEspecificacaoComponente());
            System.out.println("Detalhamento da rede ok...");

            for (int i = 0; i < componentes.size(); i++) {
                EspecificacaoComponente esAtual = componentes.get(i);

                if (esAtual.getTipoComponente().equals(TipoComponente.DISCO)) {
                    maquinaRepository.setMaquinaUltrassomEspec(100.0,
                            maquinaRepository.getMaquinaUltrassom().getIdMaquina(), esAtual.getIdEspecificacaoComponente());
                }
            }
            System.out.println("Detalhamento de discos ok...");
            System.out.println("Configuração e detalhamento finalizados...");
            System.out.println("=============================================================================");
            startComponentMonitoration();
        }
    }

    public void startComponentMonitoration() {
        ConexaoFornecedor con = new ConexaoFornecedor();
        if (!maquinaRepository.verifyMachineAutorization()) {
            System.out.println("Maquina não está autorizada a operar!");
            startCompEspecification();
        } else {
            System.out.println("Iniciando preparação de monitoramento");

            List<EspecificacaoComponente> especificacaoComponente = maquinaRepository.getEspecificacoesComponente();
            List<MaquinaUltrassomEspec> maquinaUltrassomEspec = maquinaRepository.getMaquinasUltrassomEspecs();

            List<Volume> discos = looca.getGrupoDeDiscos().getVolumes();

            Integer fkCpuEspec = especificacaoComponente.stream()
                    .filter(e -> e.getTipoComponente().equals(TipoComponente.CPU))
                    .findFirst().get().getIdEspecificacaoComponente();

            Integer fkRamEspec = especificacaoComponente.stream()
                    .filter(e -> e.getTipoComponente().equals(TipoComponente.RAM))
                    .findFirst().get().getIdEspecificacaoComponente();

            Integer fkRedeEspec = especificacaoComponente.stream()
                    .filter(e -> e.getTipoComponente().equals(TipoComponente.REDE))
                    .findFirst().get().getIdEspecificacaoComponente();
            System.out.println("Especificação de componente ok...");

            Integer fkCpu = maquinaUltrassomEspec.stream().filter(e -> e.getFkEspecificacaoComponente()
                    .equals(fkCpuEspec)).findFirst().get().getIdEspecificacaoComponenteMaquina();

            Integer fkRam = maquinaUltrassomEspec.stream().filter(e -> e.getFkEspecificacaoComponente()
                    .equals(fkRamEspec)).findFirst().get().getIdEspecificacaoComponenteMaquina();

            Integer fkRede = maquinaUltrassomEspec.stream().filter(e -> e.getFkEspecificacaoComponente()
                    .equals(fkRedeEspec)).findFirst().get().getIdEspecificacaoComponenteMaquina();

            List<EspecificacaoComponente> componentesDisc = especificacaoComponente.stream()
                    .filter(e -> e.getTipoComponente().equals(TipoComponente.DISCO)).toList();
            System.out.println("Especificação de componente maquina ok...");

            ValidadorComponente validador = new ValidadorComponente();

            new Timer().scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    if (!maquinaRepository.verifyMachineAutorization()) {
                        System.out.println("Maquina não autorizada! Contate  o seu administrador!");
                    } else {

                        try {
                            validador.validarCpu(looca.getProcessador(), fkCpu);
                        } catch (ValidacaoException e) {
                            System.out.println(e);
                        }

                        try {
                            validador.validarRam(looca.getMemoria(), fkRam);
                        } catch (ValidacaoException e) {
                            System.out.println(e);
                        }

                        for (int i = 0; i < componentesDisc.size(); i++) {
                            try {
                                System.out.println("tamanho da componentes " + componentesDisc.size());
                                System.out.println("VOLTA " + i);
                                EspecificacaoComponente especAtual = componentesDisc.get(i);

                                Integer fkDiscoEspec = especAtual.getIdEspecificacaoComponente();

                                Integer fkDisco = maquinaUltrassomEspec.stream().filter(e -> e.getFkEspecificacaoComponente()
                                        .equals(fkDiscoEspec)).findFirst().get().getIdEspecificacaoComponenteMaquina();

                                Volume discoAtual = discos.stream().filter(e -> e.getUUID()
                                        .equals(especAtual.getNumeroSerial())).findFirst().get();
                                if (convertBytesToGB(discoAtual.getTotal()) >= 1) {
                                    validador.validarDisco(discoAtual, fkDisco);
                                }

                            } catch (ValidacaoException e) {
                                System.out.println(e);
                            }
                        }
                        validador.validarRede(fkRede);

                        con.execLog("34.235.138.209", userRepository
                                        .getAdministrador().getNomeAdministrador(),
                                maquinaRepository.getMaquinaUltrassom().getStatusMaquina(),
                                maquinaRepository.getMaquinaUltrassom().getIdMaquina());
                    }
                }
            }, 0, 10000);
        }
    }

    public Double convertBytesToGB(long bytes) {
        return bytes / (1024.0 * 1024.0 * 1024.0);
    }

    public Double convertBytesToMB(long bytes) {
        return bytes / (1024.0 * 1024.0);
    }
}
