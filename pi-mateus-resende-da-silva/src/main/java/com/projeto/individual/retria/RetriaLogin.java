package com.projeto.individual.retria;

import com.github.britooo.looca.api.core.Looca;
import com.github.seratch.jslack.api.model.User;
import com.projeto.individual.retria.domain.services.Monitoramento;
import com.projeto.individual.retria.repository.UserRepository;

import java.util.Scanner;

public class RetriaLogin {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        UserRepository userRepository = new UserRepository();
        MenuFunctions menu = new MenuFunctions();
        Monitoramento service = new Monitoramento();

        Integer escolha = 0;
        String email;
        String senha;
        Boolean passou = false;

        menu.plotarIntroducaoAscii();
        menu.plotarIntroducao();

        menu.plotarMenu();
        escolha = leitor.nextInt();

        switch (escolha) {
            case 1:
                System.out.println("Você escolheu ====> Realizar login");
                System.out.println("Insira o Email");
                email = leitor.next();
                leitor.nextLine();
                System.out.println("Insira a senha");
                senha = leitor.nextLine();

                if (userRepository.existsAdministradorByEmailAndSenha(email,senha)) {
                    System.out.println("Email validao com sucesso! Iniciando programa!");
                    service.startMachineSettings(userRepository);
                } else {
                    System.out.println("Email ou senha inválidos! Tente novamente!");
                }
                break;
            case 0:
                System.out.println("Encerrando o programa!");
                System.out.println("Obrigado por utilizar a retria!");
                System.out.println("Até logo!");
                break;
            default:
                System.out.println("Opção inválida! Tente novamente");
        }
    }
}
