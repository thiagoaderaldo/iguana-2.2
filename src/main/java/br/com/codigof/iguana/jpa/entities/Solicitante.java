/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.codigof.iguana.jpa.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author thiago
 */
@Entity
@Table(name = "solicitante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitante.findAll", query = "SELECT s FROM Solicitante s"),
    @NamedQuery(name = "Solicitante.findById", query = "SELECT s FROM Solicitante s WHERE s.id = :id"),
    @NamedQuery(name = "Solicitante.findByNome", query = "SELECT s FROM Solicitante s WHERE s.nome = :nome"),
    @NamedQuery(name = "Solicitante.findByEmail", query = "SELECT s FROM Solicitante s WHERE s.email = :email"),
    @NamedQuery(name = "Solicitante.findByTelefone", query = "SELECT s FROM Solicitante s WHERE s.telefone = :telefone")})
public class Solicitante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nome")
    private String nome;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "email")
    private String email;
    @Size(max = 45)
    @Column(name = "telefone")
    private String telefone;
    @JoinColumn(name = "unidade_organizacional", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UnidadeOrganizacional unidadeOrganizacional;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitante")
    private Collection<Solicitacoes> solicitacoesCollection;

    public Solicitante() {
    }

    public Solicitante(Integer id) {
        this.id = id;
    }

    public Solicitante(Integer id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public UnidadeOrganizacional getUnidadeOrganizacional() {
        return unidadeOrganizacional;
    }

    public void setUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional) {
        this.unidadeOrganizacional = unidadeOrganizacional;
    }

    @XmlTransient
    public Collection<Solicitacoes> getSolicitacoesCollection() {
        return solicitacoesCollection;
    }

    public void setSolicitacoesCollection(Collection<Solicitacoes> solicitacoesCollection) {
        this.solicitacoesCollection = solicitacoesCollection;
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
        if (!(object instanceof Solicitante)) {
            return false;
        }
        Solicitante other = (Solicitante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "br.com.codigof.iguana.jpa.entities.Solicitante[ id=" + id + " ]";
        return getNome();
    }
    
}
