/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wsdeliveryorder;

import entities.DeliveryCompany;
import entities.DeliveryOrder;
import entities.OrderBook;
import facades.DeliveryOrderFacade;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author jaimelimonsamperio
 */
@WebService(serviceName = "WsDeliveryOrder")
public class WsDeliveryOrder {

    @EJB
    private DeliveryOrderFacade ejbRef;
    
    /**
     * Operacion de un servicio web implementado con JAX-WS
     * que emite la orden de envio de un producto.
     *
     * @param idEmpresa
     * @param idPedido Identificador de pedido
     * @return Tiempo de reparto en dias
     */
    @WebMethod(operationName = "deliverOrder")
    public int deliverOrder(
        @WebParam(name = "idEmpresa") int idEmpresa,
        @WebParam(name = "idPedido") int idPedido
    ) { 
        // Delivery days
        Random rnd = new Random();
        int deliveryDays = rnd.nextInt(15)+1;
        
        // Delivery company
        DeliveryCompany company = new DeliveryCompany();
        company.setIddelivery(idPedido);
        
        // Order book
        OrderBook order = new OrderBook();
        order.setOrderid(idPedido);
        
        // Delivery order
        DeliveryOrder deliveryOrder = new DeliveryOrder();
        deliveryOrder.setOrderid(order);
        deliveryOrder.setDeliverorderid(idPedido);
        deliveryOrder.setIddelivery(company);
        deliveryOrder.setDeliverydays(deliveryDays);
        
        System.out.println(deliveryOrder.toString());
        
        // Insert into DB
        create(deliveryOrder);

        return deliveryDays; //Reparto entre 1 y 15 dias
    }

    @WebMethod(operationName = "create")
    @Oneway
    public void create(@WebParam(name = "entity") DeliveryOrder entity) {
        ejbRef.create(entity);
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
