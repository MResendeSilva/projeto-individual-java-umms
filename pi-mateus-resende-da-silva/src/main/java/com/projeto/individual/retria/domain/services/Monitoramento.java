package com.projeto.individual.retria.domain.services;

public class Monitoramento {
    public Double convertBytesToGB(long bytes) {
        return bytes / (1024.0 * 1024.0 * 1024.0);
    }
    public Double convertBytesToMB(long bytes) {
        return bytes / (1024.0 * 1024.0);
    }
}
