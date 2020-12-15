/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojotiendaelectronica;


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
        
        java.util.List<webservices.Books> listBooks = findByIsbn(1);
        listBooks.forEach((b) -> {
            System.out.println("ISBN: " + b.getIsbn() + ", Precio: " + b.getPrice() 
                    + ", Units available: " + b.getUnitsavailable() + ", Units on hold: " +b.getUnitsonhold());
        });
        System.out.println(comprobarStock(1, 1));
        holdStock(1,7);
        java.util.List<webservices.Books> listBooks2 = findByIsbn(1);
        listBooks2.forEach((b) -> {
            System.out.println("ISBN: " + b.getIsbn() + ", Precio: " + b.getPrice() 
                    + ", Units available: " + b.getUnitsavailable() + ", Units on hold: " +b.getUnitsonhold());
        });

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

    private static void holdStock(int isbn, int unidades) {
        webservices.WSAlmacenService service = new webservices.WSAlmacenService();
        webservices.WSAlmacen port = service.getWSAlmacenPort();
        port.holdStock(isbn, unidades);
    }
    
}
