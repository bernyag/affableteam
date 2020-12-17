/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wsclient;

import entities.Client;
import facades.ClientFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author jaimelimonsamperio
 */
@WebService(serviceName = "WsClient")
public class WsClient {

    @EJB
    private ClientFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "create")
    public int create(@WebParam(name = "entity") Client entity) {
        ejbRef.create(entity);
        return entity.getClientid();
    }

    @WebMethod(operationName = "edit")
    @Oneway
    public void edit(@WebParam(name = "entity") Client entity) {
        ejbRef.edit(entity);
    }

    @WebMethod(operationName = "remove")
    @Oneway
    public void remove(@WebParam(name = "entity") Client entity) {
        ejbRef.remove(entity);
    }

    @WebMethod(operationName = "find")
    public Client find(@WebParam(name = "id") Object id) {
        return ejbRef.find(id);
    }

    @WebMethod(operationName = "findAll")
    public List<Client> findAll() {
        return ejbRef.findAll();
    }

    @WebMethod(operationName = "findRange")
    public List<Client> findRange(@WebParam(name = "range") int[] range) {
        return ejbRef.findRange(range);
    }

    @WebMethod(operationName = "count")
    public int count() {
        return ejbRef.count();
    }

    /**
     * Web service operation
     * @param idClt
     * @return 
     */
    @WebMethod(operationName = "findClientById")
    public Client findClientById(@WebParam(name = "idClt") int idClt) {
        
        return ejbRef.findById(idClt);
    }
    
    
    
}
