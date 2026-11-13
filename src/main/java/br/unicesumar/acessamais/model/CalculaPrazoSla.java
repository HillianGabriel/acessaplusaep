package br.unicesumar.acessamais.model;

/** Contrato do POLIMORFISMO: cada tipo de encaminhamento calcula o SLA do seu jeito. */
public interface CalculaPrazoSla {
    Integer calcularPrazoSLA();
}
