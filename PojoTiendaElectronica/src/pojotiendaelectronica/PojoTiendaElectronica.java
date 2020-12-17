/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojotiendaelectronica;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import wsalmacen.Books;
import wsclient.Client;
import wscobro.Exception_Exception;
import wsdeliverycompany.DeliveryCompany;
import wsdeliveryorder.DeliveryOrder;

/**
 *
 * @author bernardoaltamirano
 */
public class PojoTiendaElectronica {

    /**
     * @param args the command line arguments
     * @throws wscobro.Exception_Exception
     */
    public static void main(String[] args) throws Exception_Exception {
        try {
            final int ISBN = 1;
            final int UNITS = 1;
            final int COMPANYID = 1;
            final int CLIENTID = 1;
            
            // Create a new delivery company
//            DeliveryCompany company = new DeliveryCompany();
//            company.setName("Super delivery company");
//            int companyId = (int)createDeliveryCompany(company);
//            
//            // Create a new customer
//            Client client = new Client();
//            client.setBalance(BigDecimal.valueOf(2000));
//            int clientId = createClient(client);
            
            Books b = findByIsbn(ISBN);
            System.out.println("----------------------------ORIGINAL STOCK------------------------------"+ "\n\tISBN: " + b.getIsbn() + ", Precio: " + b.getPrice()
                    + ", Units available: " + b.getUnitsavailable() + ", Units on hold: " +b.getUnitsonhold() +"\n------------------------------------------------------------------------\n");
            
            if (startOrder(ISBN, UNITS))
                System.out.println("An order has been created for the book with ISBN = " + ISBN + "."+"\n\t" + UNITS + " unit(s) have been placed on hold while validating the account's funds...\n");
        
            b = findByIsbn(ISBN);
            System.out.println("----------------------------STOCK AFTER HOLD----------------------------"+ "\n\tISBN: " + b.getIsbn() + ", Precio: " + b.getPrice()
                    + ", Units available: " + b.getUnitsavailable() + ", Units on hold: " +b.getUnitsonhold() +"\n------------------------------------------------------------------------\n");
            
            // Try to create a new order for given customer
            // This may fail if customer balance is less that order total amount
            int orderId = startPayment(CLIENTID, ISBN, UNITS);
            System.out.println("Order created with orderId: " + orderId);
            
            // Create delivery order given orderId and deliveryCompanyId
            int deliveryEstimate = createDeliverOrder(COMPANYID, orderId);
            System.out.println("Estimated delivery days for this order: " + deliveryEstimate);
            
            // Try to create a second order
            // This one should fail bc customer has not enough money :(
            System.out.println("Trying to create a new order for same customer...");
            int otherOrderId = startPayment(CLIENTID, ISBN, UNITS);
            System.out.println("Estimated delivery days for this order: " + otherOrderId);
            
        } catch (wsalmacen.Exception_Exception ex) {
            System.err.println(ex.getMessage());
        } catch (wscobro.Exception_Exception ex){
            System.err.println(ex.getMessage());
        }
    }

    private static Books findByIsbn(int isbn) {
        wsalmacen.WSAlmacenService service = new wsalmacen.WSAlmacenService();
        wsalmacen.WSAlmacen port = service.getWSAlmacenPort();
        return port.findByIsbn(isbn);
    }

    private static boolean startOrder(int isbn, int units) throws wsalmacen.Exception_Exception {
        wsalmacen.WSAlmacenService service = new wsalmacen.WSAlmacenService();
        wsalmacen.WSAlmacen port = service.getWSAlmacenPort();
        return port.startOrder(isbn, units);
    }
    
    // WS Cobro
    private static int startPayment(int idClt, int isbn, int units) throws Exception_Exception {
        wscobro.WSCobro_Service service = new wscobro.WSCobro_Service();
        wscobro.WSCobro port = service.getWSCobroPort();
        return port.startPayment(idClt, isbn, units);
    }

    // WS Client

    private static Client findClientById(int idClt) {
        wsclient.WsClient_Service service = new wsclient.WsClient_Service();
        wsclient.WsClient port = service.getWsClientPort();
        return port.findClientById(idClt);
    }

    private static long createDeliveryCompany(wsdeliverycompany.DeliveryCompany entity) {
        wsdeliverycompany.WsDeliveryCompany_Service service = new wsdeliverycompany.WsDeliveryCompany_Service();
        wsdeliverycompany.WsDeliveryCompany port = service.getWsDeliveryCompanyPort();
        return port.create(entity);
    }

    private static int createDeliverOrder(int idEmpresa, int idPedido) {
        wsdeliveryorder.WsDeliveryOrder_Service service = new wsdeliveryorder.WsDeliveryOrder_Service();
        wsdeliveryorder.WsDeliveryOrder port = service.getWsDeliveryOrderPort();
        return port.deliverOrder(idEmpresa, idPedido);
    }

    private static int createClient(wsclient.Client entity) {
        wsclient.WsClient_Service service = new wsclient.WsClient_Service();
        wsclient.WsClient port = service.getWsClientPort();
        return port.create(entity);
    }

}