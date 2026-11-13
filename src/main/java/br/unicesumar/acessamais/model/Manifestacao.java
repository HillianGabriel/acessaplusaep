package br.unicesumar.acessamais.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "manifestacoes")
public class Manifestacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estudante_id")
    private Estudante estudante;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_barreira", nullable = false, length = 30)
    private TipoBarreira tipoBarreira;

    @Column(nullable = false, length = 150)
    private String local;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusManifestacao status = StatusManifestacao.RECEBIDA;

    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro;

    /** COMPOSIÇÃO: o encaminhamento nasce e morre com a manifestação. */
    @OneToOne(mappedBy = "manifestacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Encaminhamento encaminhamento;

    @PrePersist
    void aoPersistir() { this.dataRegistro = LocalDateTime.now(); }

    public Integer getId() { return id; }
    public Estudante getEstudante() { return estudante; }
    public void setEstudante(Estudante estudante) { this.estudante = estudante; }
    public TipoBarreira getTipoBarreira() { return tipoBarreira; }
    public void setTipoBarreira(TipoBarreira tipoBarreira) { this.tipoBarreira = tipoBarreira; }
    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public StatusManifestacao getStatus() { return status; }
    public void setStatus(StatusManifestacao status) { this.status = status; }
    public LocalDateTime getDataRegistro() { return dataRegistro; }
    public Encaminhamento getEncaminhamento() { return encaminhamento; }
    public void setEncaminhamento(Encaminhamento encaminhamento) { this.encaminhamento = encaminhamento; }
}
