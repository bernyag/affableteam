/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojotiendaelectronica;

import java.math.BigDecimal;
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
        final int ISBN = 1;
        final int UNITS = 3;
        Books b = findByIsbn(ISBN);
        System.out.println("ISBN: " + b.getIsbn() + ", Precio: " + b.getPrice() 
                + ", Units available: " + b.getUnitsavailable() + ", Units on hold: " +b.getUnitsonhold());
        System.out.println(startOrder(ISBN, UNITS));
        b = findByIsbn(ISBN);
        System.out.println("ISBN: " + b.getIsbn() + ", Precio: " + b.getPrice() 
                + ", Units available: " + b.getUnitsavailable() + ", Units on hold: " +b.getUnitsonhold());
        
        
        // Test WS Cobro
        startPayment(1, ISBN, UNITS); //Returns new order ID (int)
        
        // TODO: startPayment method should return orderId
        
        // Create a new delivery company
        DeliveryCompany company = new DeliveryCompany();
        company.setName("Super delivery company");
        company.setIddelivery(1);
        
        // Get company id after creation
        int companyId = (int)createDeliveryCompany(company);
        System.out.println("Company id: " + companyId);
        
        // Create delivery order getting delivery estimate
        // TODO: Use orderId returned from startPayment
        int deliveryEstimate = createDeliverOrder(companyId, 1);
        System.out.println("Estimated delivery in days: " + deliveryEstimate);
        
    }

    private static Books findByIsbn(int isbn) {
        wsalmacen.WSAlmacenService service = new wsalmacen.WSAlmacenService();
        wsalmacen.WSAlmacen port = service.getWSAlmacenPort();
        return port.findByIsbn(isbn);
    }

    private static String startOrder(int isbn, int units) {
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

}