/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Books;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author bernardoaltamirano
 */
@Stateless
public class BooksFacade extends AbstractFacade<Books> {

    @PersistenceContext(unitName = "TiendaElectronicaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BooksFacade() {
        super(Books.class);
    }
    
    public Books findByIsbn(int isbn)
    {
        em = getEntityManager();
        TypedQuery<Books> query = em.createNamedQuery("Books.findByIsbn", Books.class);
        query.setParameter("isbn", isbn);
        java.util.List<Books> lista= query.getResultList();

        return lista;
    }
    
    public String unitsAvailable(int isbn)
    {
        String res = "That ISBN is not available";
        em = getEntityManager();
        Query query = em.createQuery("SELECT b.unitsavailable FROM Books b WHERE b.isbn = " + isbn);
        System.err.println(query.getResultList());
        java.util.List<Integer> lista= query.getResultList();
        res = lista.get(0).toString();

        return res;
    }
    
    public void holdStock(int isbn, int units)
    {
        em = getEntityManager();
        TypedQuery<Books> query = em.createNamedQuery("Books.holdStock", Books.class);
        //query.setParameter("isbn", isbn);
        //query.setParameter("units", units);          
    }
    
}
