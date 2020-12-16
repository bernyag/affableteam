/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import entities.Books;
import entities.OrderBook;
import entities.Client;
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

    /**
     * Web service operation
     */
    @WebMethod(operationName = "updateBalance")
    public int updateBalance(@WebParam(name = "idClte") int idClte, @WebParam(name = "newBalance") String newBalance) {
        int res;
        res = ejbRef.updateBalance(idClte, newBalance);
        return res;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "discountHold")
    public int discountHold(@WebParam(name = "isbn") int isbn, @WebParam(name = "units") int units) {
        return ejbRef.discountHold(isbn, units);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "reStockHold")
    public int reStockHold(@WebParam(name = "isbn") int isbn, @WebParam(name = "units") int units) {
        return ejbRef.reStockHold(isbn, units);
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "startPayment")
    public String startPayment(@WebParam(name = "idClt") int idClt, @WebParam(name = "isbn") int isbn, @WebParam(name = "units") int units) {
        String res = "";
        if (Integer.toString(isbn).length() >= 1) { // TODO: Check that the ISBN used is in the 13 digit ISBN format 
            Books bk = ejbRef2.findByIsbn(isbn);
            if(bk.getIsbn()!= -1){
                if(units >= 1){
                    Client clt = ejbRefClient.findById(idClt);
                    if(clt.getClientid()!=-1){
                        String balance = getNewBalance(isbn, idClt, units);
                        double bal = Double.parseDouble(balance);
                        BigDecimal monto = bk.getPrice();  
                        BigDecimal uds = new BigDecimal(units);
                        monto = monto.multiply(uds);
                        
                        if(bal>=0){ //Check if the client has enough credits to proceed with the purchase
                            updateBalance(idClt, balance); //Charges the client
                            discountHold(isbn, units); //Discounts the previously held books

                            OrderBook ob = new OrderBook(); //Insert new order in table OrderBook
                            ob.setClientid(clt);
                            ob.setIsbn(bk);
                            ob.setFinalcost(monto);
                            ob.setUnitsordered(units);
                            create(ob);

                            res = "An order has been created for the book with ISBN = " + isbn + "."
                                            + "\n\t A new invoice has been created with value = INV"+ob.getOrderid()
                                            + "\n\t Client "+idClt+" new balance is $"+balance ;
                        }
                        else{ // The client hasn't enough credits
                            discountHold(isbn, units); //discounts the previously hold books
                            reStockHold(isbn, units);
                            res = "There are unsufficient credits in the client's account to complete the purchase. The total amount is $"+monto+", but the client has $"+clt.getBalance();
                        }
                    }else{
                        res = "The client with ClientId = "+idClt+" is not registered in the database. Please try with another ClientId";
                    }
                }
                else{
                    res = "Please, enter a number of units bigger than 0";
                }
            } else{
                res = "The booh with ISBN ="+isbn+" is not registered in the database. Please try with another ISBN.";
            }
        }else{
            res = "Please enter an ISBN with length of 13 digits.";
        }
        return res;
    }
    
}
