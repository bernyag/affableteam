/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import entities.OrderBook;
import facades.BooksFacade;
import facades.ClientFacade;
import facades.OrderBookFacade;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author santiagoborobia
 */
@WebService(serviceName = "WSCobro")
public class WSCobro {

    @EJB
    private OrderBookFacade ejbRef;
    @EJB
    private BooksFacade ejbRef2;
    @EJB
    private ClientFacade ejbRefClient;

    @WebMethod(operationName = "create")
    @Oneway
    public void create(@WebParam(name = "entity") OrderBook entity) {
        ejbRef.create(entity);
    }

    @WebMethod(operationName = "edit")
    @Oneway
    public void edit(@WebParam(name = "entity") OrderBook entity) {
        ejbRef.edit(entity);
    }

    @WebMethod(operationName = "remove")
    @Oneway
    public void remove(@WebParam(name = "entity") OrderBook entity) {
        ejbRef.remove(entity);
    }

    @WebMethod(operationName = "find")
    public OrderBook find(@WebParam(name = "id") Object id) {
        return ejbRef.find(id);
    }

    @WebMethod(operationName = "findAll")
    public List<OrderBook> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<OrderBook> findRange(@WebParam(name = "range") int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int count() {
        return ejbRef.count();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getNewBalance")
    public String getNewBalance(@WebParam(name = "isbn") int isbn, @WebParam(name = "idCliente") int idCliente, @WebParam(name = "cantLibros") int cantLibros) {
        String res;
        res = ejbRef.getNewBalance(isbn, cantLibros, idCliente);
        return res;
    }
    
    
    
    
}
