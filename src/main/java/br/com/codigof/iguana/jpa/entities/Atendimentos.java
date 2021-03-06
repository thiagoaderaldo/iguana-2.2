/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.codigof.iguana.jpa.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author thiago
 */
@Entity
@Table(name = "atendimentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atendimentos.findAll", query = "SELECT a FROM Atendimentos a"),
    @NamedQuery(name = "Atendimentos.findById", query = "SELECT a FROM Atendimentos a WHERE a.id = :id"),
    @NamedQuery(name = "Atendimentos.findByDataAtendimento", query = "SELECT a FROM Atendimentos a WHERE a.dataAtendimento = :dataAtendimento"),
    @NamedQuery(name = "Atendimentos.findByHoraAtendimento", query = "SELECT a FROM Atendimentos a WHERE a.horaAtendimento = :horaAtendimento"),
    @NamedQuery(name = "Atendimentos.findByObservacao", query = "SELECT a FROM Atendimentos a WHERE a.observacao = :observacao"),
    @NamedQuery(name = "Atendimentos.findByMarcadores", query = "SELECT a FROM Atendimentos a WHERE a.marcadores = :marcadores"),
    @NamedQuery(name = "Atendimentos.findBySolucao", query = "SELECT a FROM Atendimentos a WHERE a.solucao = :solucao"),
    @NamedQuery(name = "Atendimentos.findByResponsavel", query = "SELECT a FROM Atendimentos a WHERE a.responsavel = :responsavel")})
public class Atendimentos implements Serializable {

    @Lob
    @Size(max = 65535)
    @Column(name = "solucao")
    private String solucao;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_atendimento")
    @Temporal(TemporalType.DATE)
    private Date dataAtendimento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora_atendimento")
    @Temporal(TemporalType.TIME)
    private Date horaAtendimento;
    @Size(max = 45)
    @Column(name = "observacao")
    private String observacao;
    @Size(max = 45)
    @Column(name = "marcadores")
    private String marcadores;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "responsavel")
    private String responsavel;
    @JoinColumn(name = "id_solicitacao", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Solicitacoes idSolicitacao;

    public Atendimentos() {
    }

    public Atendimentos(Long id) {
        this.id = id;
    }

    public Atendimentos(Long id, Date dataAtendimento, Date horaAtendimento, String responsavel) {
        this.id = id;
        this.dataAtendimento = dataAtendimento;
        this.horaAtendimento = horaAtendimento;
        this.responsavel = responsavel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(Date dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public Date getHoraAtendimento() {
        return horaAtendimento;
    }

    public void setHoraAtendimento(Date horaAtendimento) {
        this.horaAtendimento = horaAtendimento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getMarcadores() {
        return marcadores;
    }

    public void setMarcadores(String marcadores) {
        this.marcadores = marcadores;
    }


    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public Solicitacoes getIdSolicitacao() {
        return idSolicitacao;
    }

    public void setIdSolicitacao(Solicitacoes idSolicitacao) {
        this.idSolicitacao = idSolicitacao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Atendimentos)) {
            return false;
        }
        Atendimentos other = (Atendimentos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.codigof.iguana.jpa.entities.Atendimentos[ id=" + id + " ]";
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }
    
}
