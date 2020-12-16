/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Client;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author jaimelimonsamperio
 */
@Stateless
public class ClientFacade extends AbstractFacade<Client> {

    @PersistenceContext(unitName = "TiendaElectronicaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientFacade() {
        super(Client.class);
    }
    
    public Client findById(int idClt)
    {
        Client c = new Client(-1);    //return a Client with -1 for an ID that is not registered in the Database
        em = getEntityManager();
        TypedQuery<Client> query = em.createNamedQuery("Client.findByClientid", Client.class);
        query.setParameter("clientid", idClt);
        java.util.List<Client> lista= query.getResultList();
        if (!lista.isEmpty())
            c = lista.get(0);
        return c;
    }
    
}
