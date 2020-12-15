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
    
}
