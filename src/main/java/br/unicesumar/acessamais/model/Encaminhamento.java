package br.unicesumar.acessamais.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/** Classe abstrata que implementa a interface; as filhas sobrescrevem (@Override) o cálculo do SLA. */
@Entity
@Table(name = "encaminhamentos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_encaminhamento", length = 20)
public abstract class Encaminhamento implements CalculaPrazoSla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manifestacao_id", nullable = false)
    private Manifestacao manifestacao;

    @Column(name = "setor_responsavel", length = 50)
    private String setorResponsavel;

    @Column(name = "sla_prazo_dias")
    private Integer slaPrazoDias;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;

    @JsonIgnore
    public abstract String getSetorPadrao();

    public Integer getId() { return id; }
    public Manifestacao getManifestacao() { return manifestacao; }
    public void setManifestacao(Manifestacao manifestacao) { this.manifestacao = manifestacao; }
    public String getSetorResponsavel() { return setorResponsavel; }
    public void setSetorResponsavel(String setorResponsavel) { this.setorResponsavel = setorResponsavel; }
    public Integer getSlaPrazoDias() { return slaPrazoDias; }
    public void setSlaPrazoDias(Integer slaPrazoDias) { this.slaPrazoDias = slaPrazoDias; }
    public LocalDateTime getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(LocalDateTime dataConclusao) { this.dataConclusao = dataConclusao; }
}
