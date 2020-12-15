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
        // TODO code application logic here
        
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


    private static java.util.List<webservices.Books> findByIsbn(int isbn) {
        webservices.WSAlmacenService service = new webservices.WSAlmacenService();
        webservices.WSAlmacen port = service.getWSAlmacenPort();
        return port.findByIsbn(isbn);
    }

    private static boolean comprobarStock(int isbn, int unidades) {
        webservices.WSAlmacenService service = new webservices.WSAlmacenService();
        webservices.WSAlmacen port = service.getWSAlmacenPort();
        return port.comprobarStock(isbn, unidades);
    }

    private static void holdStock(int isbn, int unidades) {
        webservices.WSAlmacenService service = new webservices.WSAlmacenService();
        webservices.WSAlmacen port = service.getWSAlmacenPort();
        port.holdStock(isbn, unidades);
    }
    
}
