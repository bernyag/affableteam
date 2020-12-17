/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.DeliveryCompany;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author jaimelimonsamperio
 */
@Stateless
public class DeliveryCompanyFacade extends AbstractFacade<DeliveryCompany> {

    @PersistenceContext(unitName = "TiendaElectronicaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DeliveryCompanyFacade() {
        super(DeliveryCompany.class);
    }
    
    public DeliveryCompany findById(int deliveryId)
    {
        DeliveryCompany dc = new DeliveryCompany(-1);
        em = getEntityManager();
        TypedQuery<DeliveryCompany> query = em.createNamedQuery("DeliveryCompany.findByIddelivery", DeliveryCompany.class);
        query.setParameter("iddelivery", deliveryId);
        java.util.List<DeliveryCompany> lista= query.getResultList();
        if (!lista.isEmpty())
            dc = lista.get(0);
        return dc;
    }
    
}
