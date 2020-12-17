/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.OrderBook;
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
public class OrderBookFacade extends AbstractFacade<OrderBook> {

    @PersistenceContext(unitName = "TiendaElectronicaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderBookFacade() {
        super(OrderBook.class);
    }
    
    
    public OrderBook findByOrderId(int orderid)
    {
        OrderBook b = new OrderBook(-1);    //return a book with -1 for an ISBN that is not registered in the Database
        em = getEntityManager();
        TypedQuery<OrderBook> query = em.createNamedQuery("Books.findByOrderid", OrderBook.class);
        query.setParameter("orderid", orderid);
        java.util.List<OrderBook> lista= query.getResultList();
        if (!lista.isEmpty())
            b = lista.get(0);
        return b;
    }
    
    public String getNewBalance(int isbn, int units, int clientId)
    {
        BigDecimal res;
        em = getEntityManager();
        Query query = em.createQuery("SELECT (c.balance-b.price*"+units+") FROM Client c, Books b  WHERE c.clientid="+clientId+" AND b.isbn = "+isbn);
        System.err.println(query.getResultList());
        java.util.List<BigDecimal> lista= query.getResultList();
        res = lista.get(0);

        return ""+res;
    }
    
    public int updateBalance( int clientId, String newBalance){
        em = getEntityManager();
        Query query = em.createQuery("UPDATE Client SET balance ="+newBalance+" WHERE clientid = "+clientId);
        System.err.println(query.executeUpdate());
        
        return query.executeUpdate();
    }
    
    public int discountHold( int isbn, int units){
        em = getEntityManager();
        Query query = em.createQuery("UPDATE Books b SET b.unitsonhold = (b.unitsonhold-"+units+") WHERE b.isbn = "+isbn);
        
        return query.executeUpdate();
    }
    
    public int reStockHold( int isbn, int units){
        em = getEntityManager();
        Query query = em.createQuery("UPDATE Books b SET b.unitsavailable = (b.unitsavailable+"+units+") WHERE b.isbn = "+isbn);
        
        return query.executeUpdate();
    }
    
    
    
}
