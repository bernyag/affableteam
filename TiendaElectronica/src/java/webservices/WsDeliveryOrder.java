/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices;

import entities.DeliveryCompany;
import entities.DeliveryOrder;
import entities.OrderBook;
import facades.DeliveryCompanyFacade;
import facades.DeliveryOrderFacade;
import facades.OrderBookFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author jaimelimonsamperio
 */
@WebService(serviceName = "WsDeliveryOrder")
@Stateless()
public class WsDeliveryOrder {

    @EJB
    private DeliveryOrderFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @EJB
    private DeliveryCompanyFacade ejbRef2;
    
    @EJB
    private OrderBookFacade ejbRef3;
    
    @WebMethod(operationName = "create")
    @Oneway
    public void create(@WebParam(name = "entity") DeliveryOrder entity) { 
        ejbRef.create(entity);
    }
    
    @WebMethod(operationName = "createDeliveryOrder")
    @Oneway
    public void createDeliveryOrder(@WebParam(name = "iddelivery") int companyId, @WebParam(name = "orderid") int orderId) { //mandar los IDs y hacer un  delivery order  y pasarlo como entity
        DeliveryOrder entity = new DeliveryOrder();
        DeliveryCompany dc = ejbRef2.findById(companyId);
        OrderBook ob =ejbRef3.findByOrderId(orderId);
        entity.setIddelivery(dc);
        entity.setOrderid(ob);
        create(entity);
    }

    @WebMethod(operationName = "edit")
    @Oneway
    public void edit(@WebParam(name = "entity") DeliveryOrder entity) {
        ejbRef.edit(entity);
    }

    @WebMethod(operationName = "remove")
    @Oneway
    public void remove(@WebParam(name = "entity") DeliveryOrder entity) {
        ejbRef.remove(entity);
    }

    @WebMethod(operationName = "find")
    public DeliveryOrder find(@WebParam(name = "id") Object id) {
        return ejbRef.find(id);
    }

    @WebMethod(operationName = "findAll")
    public List<DeliveryOrder> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<DeliveryOrder> findRange(@WebParam(name = "range") int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int count() {
        return ejbRef.count();
    }
    
}
