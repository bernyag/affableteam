/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojotiendaelectronica;

import java.math.BigDecimal;
import webservices.Books;

/**
 *
 * @author bernardoaltamirano
 */
public class PojoTiendaElectronica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final int ISBN = 13;
        final int UNITS = 0;
        Books b = findByIsbn(ISBN);
        System.out.println("ISBN: " + b.getIsbn() + ", Precio: " + b.getPrice() 
                + ", Units available: " + b.getUnitsavailable() + ", Units on hold: " +b.getUnitsonhold());
        System.out.println(startOrder(ISBN, UNITS));
        b = findByIsbn(ISBN);
        System.out.println("ISBN: " + b.getIsbn() + ", Precio: " + b.getPrice() 
                + ", Units available: " + b.getUnitsavailable() + ", Units on hold: " +b.getUnitsonhold());
        
        
        
        // Test WS Cobro
        
        
        
        
        webservices.OrderBook ob = new webservices.OrderBook();
        
        BigDecimal balance = new BigDecimal(getNewBalance(1,2,1));
 
        System.out.println(""+balance);
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

    private static String getNewBalance(int isbn, int idCliente, int cantLibros) {
        webservices.WSCobro_Service service = new webservices.WSCobro_Service();
        webservices.WSCobro port = service.getWSCobroPort();
        return port.getNewBalance(isbn, idCliente, cantLibros);
    }
    
}