/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.codigof.iguana.beans;

import br.com.codigof.iguana.jpa.entities.Atendimentos;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author thiago
 */
@Stateless
public class AtendimentosFacade extends AbstractFacade<Atendimentos> {
    @PersistenceContext(unitName = "br.com.codigof_iguana_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AtendimentosFacade() {
        super(Atendimentos.class);
    }
    
}
