/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojotiendaelectronica;

import java.math.BigDecimal;
import webservices.Books;
import webservices.Client;

/**
 *
 * @author bernardoaltamirano
 */
public class PojoTiendaElectronica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
        System.out.println(startPayment(1, 1, 3));
        
        
        
        
    }

    private static Books findByIsbn(int isbn) {
        webservices.WSAlmacenService service = new webservices.WSAlmacenService();
        webservices.WSAlmacen port = service.getWSAlmacenPort();
        return port.findByIsbn(isbn);
    }

    private static String startOrder(int isbn, int units) {
        webservices.WSAlmacenService service = new webservices.WSAlmacenService();
        webservices.WSAlmacen port = service.getWSAlmacenPort();
        return port.startOrder(isbn, units);
    }
    
    // WS Cobro
    private static String startPayment(int idClt, int isbn, int units) {
        webservices.WSCobro_Service service = new webservices.WSCobro_Service();
        webservices.WSCobro port = service.getWSCobroPort();
        return port.startPayment(idClt, isbn, units);
    }

    // WS Client

    private static Client findClientById(int idClt) {
        webservices.WsClient_Service service = new webservices.WsClient_Service();
        webservices.WsClient port = service.getWsClientPort();
        return port.findClientById(idClt);
    }

}